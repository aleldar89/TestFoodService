package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.product.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterProductsByCategoriesUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<Product>> =
        catalogRepository.filterProductsByCategories(categoryIds)
}