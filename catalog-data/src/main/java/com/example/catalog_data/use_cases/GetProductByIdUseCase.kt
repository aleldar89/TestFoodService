package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.product.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun getProductById(id: Int): Product = catalogRepository.getProductById(id)
}