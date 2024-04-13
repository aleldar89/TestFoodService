package com.example.catalog_data

import com.example.models_api.category.Category
import com.example.models_api.product.Product
import com.example.models_api.tag.Tag
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    val categories: Flow<List<Category>>
    val tags: Flow<List<Tag>>
    val products: Flow<List<Product>>
    suspend fun getRemoteData()
    suspend fun getRemoteCategories()
    suspend fun getRemoteTags()
    suspend fun getRemoteProducts()
}