package com.example.models.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.BaseEntity
import com.example.models.DataMapper

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val name: String
) : BaseEntity(id), DataMapper<Category> {

    override fun entityToDto() = Category(id, name)
}

fun Category.toEntity() = CategoryEntity(id, name)