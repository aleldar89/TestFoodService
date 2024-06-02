package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import com.example.domain.models.TagModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalTagsUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val tags: Flow<List<TagModel>> = catalogRepository.tags
}