package com.example.models.crossref

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.models.product.ProductEntity
import com.example.models.tag.TagEntity

@Entity(
    tableName = "product_tag",
    primaryKeys = ["productId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductTagCrossRef(
    val productId: Int,
    val tagId: Int
)