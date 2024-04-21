package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.product.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalProductsUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val localProducts: Flow<List<Product>> = catalogRepository.products
}