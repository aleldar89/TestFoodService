package com.example.catalog_data

import com.example.base.BaseRepository
import com.example.models.category.Category
import com.example.models.category.toEntity
import com.example.models.product.Product
import com.example.models.product.toEntity
import com.example.models.tag.Tag
import com.example.models.tag.toEntity
import com.example.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val dao: CatalogDao,
    private val apiService: ApiService
) : BaseRepository(), CatalogRepository {

    override val categories: Flow<List<Category>> = doLocalRequest { dao.getCategories() }

    override val tags: Flow<List<Tag>> = doLocalRequest { dao.getTags() }

    override val products: Flow<List<Product>> = doLocalRequest { dao.getProducts() }

    override val productsFromCart: Flow<List<Product>> = doLocalRequest {
        dao.getProductsFromCart()
    }

    override suspend fun getRemoteData() {
        withContext(Dispatchers.IO) {
            val remoteDataDeferred = async {
                val categories = apiService.getCategories()
                val tags = apiService.getTags()
                val products = apiService.getProducts()
                Triple(categories, tags, products)
            }
            val (categories, tags, products) = remoteDataDeferred.await()
            dao.insertAllCategories(categories.map { it.toEntity() })
            dao.insertAllTags(tags.map { it.toEntity() })
            dao.insertAllProducts(products.map { it.toEntity() })
        }
    }

    override suspend fun getRemoteCategories() = dao.insertAllCategories(
        apiService.getCategories().map {
            it.toEntity()
        }
    )

    override suspend fun getRemoteTags() = dao.insertAllTags(
        apiService.getTags().map {
            it.toEntity()
        }
    )

    override suspend fun getRemoteProducts() = dao.insertAllProducts(
        apiService.getProducts().map {
            it.toEntity()
        }
    )

    override suspend fun getProductById(id: Int): Product = dao.getProductById(id).entityToDto()

    override fun filterProductsByTags(tagIds: List<Int>): Flow<List<Product>> =
        dao.filterProductsByTags(tagIds)
            .map { list ->
                list.map { entity ->
                    entity.entityToDto()
                }
            }

    override fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<Product>> =
        dao.filterProductsByCategories(categoryIds)
            .map { list ->
                list.map { entity ->
                    entity.entityToDto()
                }
            }

    override fun filterProductsByTagAndCategory(
        tagIds: List<Int>,
        categoryIds: List<Int>
    ): Flow<List<Product>> =
        combine(
            filterProductsByTags(tagIds),
            filterProductsByCategories(categoryIds)
        ) { productsByTags, productsByCategories ->
            productsByTags.intersect(productsByCategories.toSet()).toList()
        }

    override suspend fun increaseProductAmount(id: Int) = dao.increaseProductAmount(id)

    override suspend fun decreaseProductAmount(id: Int) = dao.decreaseProductAmount(id)
}