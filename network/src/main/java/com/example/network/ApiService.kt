package com.example.network

import com.example.models_api.category.Category
import com.example.models_api.product.Product
import com.example.models_api.tag.Tag
import retrofit2.http.GET

interface ApiService {

    @GET("Categories.json")
    suspend fun getCategories(): List<Category>

    @GET("Tags.json")
    suspend fun getTags(): List<Tag>

    @GET("Products.json")
    suspend fun getProducts(): List<Product>
}