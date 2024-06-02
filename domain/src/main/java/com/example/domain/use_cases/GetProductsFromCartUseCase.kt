package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromCartUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val cart: Flow<List<ProductModel>> = catalogRepository.productsFromCart
}