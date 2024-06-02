package com.example.domain

import com.example.domain.models.CategoryModel
import com.example.domain.models.ProductModel
import com.example.domain.models.TagModel
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    val categories: Flow<List<CategoryModel>>
    val tags: Flow<List<TagModel>>
    val products: Flow<List<ProductModel>>
    val productsFromCart: Flow<List<ProductModel>>
    val cartPrice: Flow<Int>
    suspend fun getRemoteData()
    suspend fun getProductById(id: Int): ProductModel
    fun filterProductsByTags(tagIds: List<Int>): Flow<List<ProductModel>>
    fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<ProductModel>>
    fun filterProductsByTagAndCategory(
        tagIds: List<Int>, categoryIds: List<Int>
    ): Flow<List<ProductModel>>

    suspend fun increaseProductAmount(id: Int)
    suspend fun decreaseProductAmount(id: Int)
}