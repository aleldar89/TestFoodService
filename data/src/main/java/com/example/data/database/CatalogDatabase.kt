package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.Converters
import com.example.data.entities.CategoryEntity
import com.example.data.entities.ProductEntity
import com.example.data.entities.TagEntity

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