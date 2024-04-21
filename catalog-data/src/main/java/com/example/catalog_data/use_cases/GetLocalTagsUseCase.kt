package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models.tag.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalTagsUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val localTags: Flow<List<Tag>> = catalogRepository.tags
}