package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterProductsByTagAndCategoryUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    fun filter(
        tagIds: List<Int>,
        categoryIds: List<Int>
    ): Flow<List<ProductModel>> =
        catalogRepository.filterProductsByTagAndCategory(tagIds, categoryIds)
}