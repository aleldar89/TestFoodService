package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String,
    val energyPer100Grams: Double,
    val proteinsPer100Grams: Double,
    val fatsPer100Grams: Double,
    val carbohydratesPer100Grams: Double,
    val tagIds: List<Int>,
    val amount: Int,
    val totalPrice: Int
)