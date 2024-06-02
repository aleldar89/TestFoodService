package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import javax.inject.Inject

class DecreaseProductAmountUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun decrease(id: Int) = catalogRepository.decreaseProductAmount(id)
}