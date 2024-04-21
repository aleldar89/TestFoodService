package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import javax.inject.Inject

class DecreaseProductAmountUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun decreaseProductAmount(id: Int) = catalogRepository.decreaseProductAmount(id)
}