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
import kotlinx.coroutines.flow.Flow
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
            getRemoteCategories()
            getRemoteTags()
            getRemoteProducts()
        }
    }

    override suspend fun getRemoteCategories() {
        apiService.getCategories().forEach {
            dao.insertCategory(it.toEntity())
        }
    }

    override suspend fun getRemoteTags() {
        apiService.getTags().forEach {
            dao.insertTag(it.toEntity())
        }
    }

    override suspend fun getRemoteProducts() {
        apiService.getProducts().forEach {
            dao.insertProduct(it.toEntity())
        }
    }

    override suspend fun getProductById(id: Int): Product = withContext(Dispatchers.IO) {
        dao.getProductById(id).entityToDto()
    }

    override fun filterProductsByTags(tagIds: List<Int>): Flow<List<Product>> =
        dao.filterProductsByTag(tagIds)
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

    override suspend fun increaseProductAmount(id: Int) = withContext(Dispatchers.IO) {
        dao.increaseProductAmount(id)
    }

    override suspend fun decreaseProductAmount(id: Int) = withContext(Dispatchers.IO) {
        dao.decreaseProductAmount(id)
    }
}