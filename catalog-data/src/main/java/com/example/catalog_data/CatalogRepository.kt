package com.example.catalog_data

import com.example.models.category.Category
import com.example.models.product.Product
import com.example.models.tag.Tag
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    val categories: Flow<List<Category>>
    val tags: Flow<List<Tag>>
    val products: Flow<List<Product>>
    val productsFromCart: Flow<List<Product>>
    suspend fun getRemoteData()
    suspend fun getRemoteCategories()
    suspend fun getRemoteTags()
    suspend fun getRemoteProducts()
    suspend fun getProductById(id: Int): Product
    fun filterProductsByTags(tagIds: List<Int>): Flow<List<Product>>
    fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<Product>>
    suspend fun increaseProductAmount(id: Int)
    suspend fun decreaseProductAmount(id: Int)
}