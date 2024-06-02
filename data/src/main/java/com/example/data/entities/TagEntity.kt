package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)