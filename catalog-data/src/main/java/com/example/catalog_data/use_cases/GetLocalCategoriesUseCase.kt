package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.category.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalCategoriesUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val localCategories: Flow<List<Category>> = catalogRepository.categories
}