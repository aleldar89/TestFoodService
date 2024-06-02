package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalProductsUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val products: Flow<List<ProductModel>> = catalogRepository.products
}