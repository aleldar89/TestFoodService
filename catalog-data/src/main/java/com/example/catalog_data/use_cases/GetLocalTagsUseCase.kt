package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import com.example.models_api.tag.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalTagsUseCase @Inject constructor(
    catalogRepository: CatalogRepository
) {
    val getLocalTags: Flow<List<Tag>> = catalogRepository.tags
}