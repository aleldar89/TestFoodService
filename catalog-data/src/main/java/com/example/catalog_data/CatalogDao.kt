package com.example.catalog_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.models.Converters
import com.example.models.category.CategoryEntity
import com.example.models.product.ProductEntity
import com.example.models.tag.TagEntity
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

    //FIXME
    @Query("""
    SELECT * FROM product
    WHERE id IN (
        SELECT productId FROM product_tag
        WHERE tagId IN (:tagIds)
        GROUP BY productId
        HAVING COUNT(DISTINCT tagId) = :tagCount
    )
""")
    fun filterProductsByTags(tagIds: List<Int>, tagCount: Int): Flow<List<ProductEntity>>

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