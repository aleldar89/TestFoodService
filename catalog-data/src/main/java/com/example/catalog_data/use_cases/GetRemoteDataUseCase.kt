package com.example.catalog_data.use_cases

import com.example.catalog_data.CatalogRepository
import javax.inject.Inject

class GetRemoteDataUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun getRemoteData() = catalogRepository.getRemoteData()
}