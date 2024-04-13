package com.example.catalog_data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface CatalogRepositoryModule {

    @Singleton
    @Binds
    fun bindsCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository
}