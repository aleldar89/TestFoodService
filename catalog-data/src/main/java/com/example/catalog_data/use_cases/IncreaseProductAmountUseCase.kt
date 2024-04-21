package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import javax.inject.Inject

class IncreaseProductAmountUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun increaseProductAmount(id: Int) = catalogRepository.increaseProductAmount(id)
}