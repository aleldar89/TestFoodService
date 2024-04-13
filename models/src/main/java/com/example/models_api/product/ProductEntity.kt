package com.example.models_api.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models_api.BaseEntity
import com.example.models_api.DataMapper

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
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
    val tagIds: List<Int>
) : BaseEntity(id), DataMapper<Product> {

    override fun entityToDto() = Product(
        id,
        categoryId,
        name,
        description,
        image,
        priceCurrent,
        priceOld,
        measure,
        measureUnit,
        energyPer100Grams,
        proteinsPer100Grams,
        fatsPer100Grams,
        carbohydratesPer100Grams,
        tagIds
    )
}

fun Product.toEntity() = ProductEntity(
    id,
    categoryId,
    name,
    description,
    image,
    priceCurrent,
    priceOld,
    measure,
    measureUnit,
    energyPer100Grams,
    proteinsPer100Grams,
    fatsPer100Grams,
    carbohydratesPer100Grams,
//    tagIds.ifEmpty { listOf() }
    tagIds
)