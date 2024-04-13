package com.example.catalog_data

import com.example.base.BaseRepository
import com.example.models_api.category.Category
import com.example.models_api.category.toEntity
import com.example.models_api.product.Product
import com.example.models_api.product.toEntity
import com.example.models_api.tag.Tag
import com.example.models_api.tag.toEntity
import com.example.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val dao: CatalogDao,
    private val apiService: ApiService,
) : BaseRepository(), CatalogRepository {

    override val categories: Flow<List<Category>> = doLocalRequest { dao.getCategories() }
    override val tags: Flow<List<Tag>> = doLocalRequest { dao.getTags() }
    override val products: Flow<List<Product>> = doLocalRequest { dao.getProducts() }

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
}