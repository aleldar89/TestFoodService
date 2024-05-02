package com.example.catalog_data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.models.Converters
import com.example.models.category.CategoryEntity
import com.example.models.product.ProductEntity
import com.example.models.tag.TagEntity

@Database(
    entities = [
        CategoryEntity::class,
        TagEntity::class,
        ProductEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CatalogDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
}