package com.example.data.network

import com.example.data.dto.CategoryDTO
import com.example.data.dto.ProductDTO
import com.example.data.dto.TagDTO
import retrofit2.http.GET

interface ApiService {

    @GET("Categories.json")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("Tags.json")
    suspend fun getTags(): List<TagDTO>

    @GET("Products.json")
    suspend fun getProducts(): List<ProductDTO>
}