package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalCategoriesUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val categories: Flow<List<CategoryModel>> = catalogRepository.categories
}