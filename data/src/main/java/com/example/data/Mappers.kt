package com.example.data

import com.example.data.dto.CategoryDTO
import com.example.data.dto.ProductDTO
import com.example.data.dto.TagDTO
import com.example.data.entities.CategoryEntity
import com.example.data.entities.ProductEntity
import com.example.data.entities.TagEntity
import com.example.domain.models.CategoryModel
import com.example.domain.models.ProductModel
import com.example.domain.models.TagModel

fun CategoryDTO.toEntity() = CategoryEntity(id = id, name = name)

fun CategoryEntity.toModel() = CategoryModel(id = id, name = name)

fun TagDTO.toEntity() = TagEntity(id = id, name = name)

fun TagEntity.toModel() = TagModel(id = id, name = name)

fun ProductDTO.toEntity() = ProductEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    image = image,
    priceCurrent = priceCurrent,
    priceOld = priceOld,
    measure = measure,
    measureUnit = measureUnit,
    energyPer100Grams = energyPer100Grams,
    proteinsPer100Grams = proteinsPer100Grams,
    fatsPer100Grams = fatsPer100Grams,
    carbohydratesPer100Grams = carbohydratesPer100Grams,
    tagIds = tagIds,
    amount = amount,
    totalPrice = totalPrice
)

fun ProductEntity.toModel() = ProductModel(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    image = image,
    priceCurrent = priceCurrent,
    priceOld = priceOld,
    measure = measure,
    measureUnit = measureUnit,
    energyPer100Grams = energyPer100Grams,
    proteinsPer100Grams = proteinsPer100Grams,
    fatsPer100Grams = fatsPer100Grams,
    carbohydratesPer100Grams = carbohydratesPer100Grams,
    tagIds = tagIds,
    amount = amount,
    totalPrice = totalPrice
)