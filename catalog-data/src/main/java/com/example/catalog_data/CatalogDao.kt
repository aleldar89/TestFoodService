package com.example.catalog_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.models_api.category.CategoryEntity
import com.example.models_api.product.ProductEntity
import com.example.models_api.tag.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Query("SELECT * FROM CategoryEntity ORDER BY id DESC")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM TagEntity ORDER BY id DESC")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM ProductEntity ORDER BY id DESC")
    fun getProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(item: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(item: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(item: ProductEntity)
}