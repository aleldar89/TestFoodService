package com.example.catalog_data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CatalogDatabaseModule {

    @Singleton
    @Provides
    fun provideCatalogDatabase(
        @ApplicationContext
        context: Context
    ): CatalogDatabase = Room
        .databaseBuilder(context, CatalogDatabase::class.java, "catalog.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCatalogDao(catalogDb: CatalogDatabase): CatalogDao = catalogDb.catalogDao()
}