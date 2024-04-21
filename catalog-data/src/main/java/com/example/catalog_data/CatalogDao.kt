package com.example.catalog_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.models.category.CategoryEntity
import com.example.models.product.ProductEntity
import com.example.models.tag.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Query("SELECT * FROM CategoryEntity ORDER BY id DESC")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM TagEntity ORDER BY id DESC")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM ProductEntity ORDER BY id DESC")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE amount > 0 ORDER BY id DESC")
    fun getProductsFromCart(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE id IN (SELECT DISTINCT id FROM ProductTagCrossRef WHERE tagId IN (:tagIds))")
    fun filterProductsByTag(tagIds: List<Int>): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE categoryId IN (:categoryIds)")
    fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(item: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(item: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(item: ProductEntity)

    @Query("UPDATE ProductEntity SET amount = amount + 1, totalPrice = totalPrice + priceCurrent WHERE id = :id")
    suspend fun increaseProductAmount(id: Int)

    @Query("UPDATE ProductEntity SET amount = amount - 1, totalPrice = totalPrice - priceCurrent WHERE id = :id")
    suspend fun decreaseProductAmount(id: Int)
}