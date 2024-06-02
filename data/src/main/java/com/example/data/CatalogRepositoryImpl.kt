package com.example.data

import com.example.data.database.CatalogDao
import com.example.data.network.ApiService
import com.example.domain.CatalogRepository
import com.example.domain.models.CategoryModel
import com.example.domain.models.ProductModel
import com.example.domain.models.TagModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val dao: CatalogDao,
    private val apiService: ApiService
) : CatalogRepository {

    override val categories: Flow<List<CategoryModel>> =
        dao.getCategories().mapToFlowOfModel {
            it.toModel()
        }

    override val tags: Flow<List<TagModel>> = dao.getTags().mapToFlowOfModel {
        it.toModel()
    }

    override val products: Flow<List<ProductModel>> = dao.getProducts().mapToFlowOfModel {
        it.toModel()
    }

    override val productsFromCart: Flow<List<ProductModel>> =
        dao.getProductsFromCart().mapToFlowOfModel {
            it.toModel()
        }

    override val cartPrice: Flow<Int> = productsFromCart.map { products ->
        products.sumOf { it.totalPrice }
    }

    override suspend fun getRemoteData(): Unit = withContext(Dispatchers.IO) {
        async {
            dao.apply {
                insertAllCategories(apiService.getCategories().map { it.toEntity() })
                insertAllTags(apiService.getTags().map { it.toEntity() })
                insertAllProducts(apiService.getProducts().map { it.toEntity() })
            }
        }.await()
    }

    override suspend fun getProductById(id: Int): ProductModel =
        dao.getProductById(id).toModel()

    override fun filterProductsByTags(tagIds: List<Int>): Flow<List<ProductModel>> =
        dao.filterProductsByTags(tagIds).mapToFlowOfModel {
            it.toModel()
        }

    override fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<ProductModel>> =
        dao.filterProductsByCategories(categoryIds).mapToFlowOfModel {
            it.toModel()
        }

    override fun filterProductsByTagAndCategory(
        tagIds: List<Int>,
        categoryIds: List<Int>
    ): Flow<List<ProductModel>> = combine(
        filterProductsByTags(tagIds), filterProductsByCategories(categoryIds)
    ) { productsByTags, productsByCategories ->
        productsByTags.intersect(productsByCategories.toSet()).toList()
    }

    override suspend fun increaseProductAmount(id: Int) = dao.increaseProductAmount(id)

    override suspend fun decreaseProductAmount(id: Int) = dao.decreaseProductAmount(id)

    private fun <E, M> Flow<List<E>>.mapToFlowOfModel(transform: (E) -> M): Flow<List<M>> =
        this.map { list ->
            list.map(transform)
        }.catch { exception ->
            throw exception
        }
}