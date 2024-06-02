package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import javax.inject.Inject

class IncreaseProductAmountUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun increase(id: Int) = catalogRepository.increaseProductAmount(id)
}