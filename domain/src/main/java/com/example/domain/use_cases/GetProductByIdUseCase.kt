package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.ProductModel
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun product(id: Int): ProductModel = catalogRepository.getProductById(id)
}