package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.CategoryEntity
import com.example.data.entities.ProductEntity
import com.example.data.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Query("SELECT * FROM category ORDER BY id DESC")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM tag ORDER BY id DESC")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM product ORDER BY id DESC")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE amount > 0 ORDER BY id DESC")
    fun getProductsFromCart(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

    @Query("SELECT * FROM product WHERE id IN (SELECT id FROM product WHERE tagIds = :tagIds)")
    fun filterProductsByTags(tagIds: List<Int>): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE categoryId IN (:categoryIds)")
    fun filterProductsByCategories(categoryIds: List<Int>): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE tagIds IN (:tagIds) AND categoryId IN (:categoryIds)")
    fun filterProductsByTagAndCategory(
        tagIds: List<Int>,
        categoryIds: List<Int>
    ): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(list: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTags(list: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(list: List<ProductEntity>)

    @Query("UPDATE product SET amount = amount + 1, totalPrice = totalPrice + priceCurrent WHERE id = :id")
    suspend fun increaseProductAmount(id: Int)

    @Query("UPDATE product SET amount = amount - 1, totalPrice = totalPrice - priceCurrent WHERE id = :id")
    suspend fun decreaseProductAmount(id: Int)
}