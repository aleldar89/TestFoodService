package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.product.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromCartUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val productsFromCart: Flow<List<Product>> = catalogRepository.productsFromCart
}