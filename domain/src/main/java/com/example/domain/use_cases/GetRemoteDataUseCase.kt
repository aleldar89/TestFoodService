package com.example.domain.use_cases

import com.example.domain.CatalogRepository
import javax.inject.Inject

class GetRemoteDataUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun data() = catalogRepository.getRemoteData()
}