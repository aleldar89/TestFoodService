package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartPriceUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val price: Flow<Int> = catalogRepository.cartPrice
}