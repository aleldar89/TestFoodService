package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    @SerializedName("price_current")
    val priceCurrent: Int,
    @SerializedName("price_old")
    val priceOld: Int?,
    val measure: Int,
    @SerializedName("measure_unit")
    val measureUnit: String,
    @SerializedName("energy_per_100_grams")
    val energyPer100Grams: Double,
    @SerializedName("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @SerializedName("fats_per_100_grams")
    val fatsPer100Grams: Double,
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @SerializedName("tag_ids")
    val tagIds: List<Int>,
    val amount: Int = 0,
    val totalPrice: Int = 0
)
