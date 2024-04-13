package com.example.models_api.tag

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models_api.BaseEntity
import com.example.models_api.DataMapper

@Entity
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val name: String
) : BaseEntity(id), DataMapper<Tag> {

    override fun entityToDto() = Tag(id, name)
}

fun Tag.toEntity() = TagEntity(id, name)