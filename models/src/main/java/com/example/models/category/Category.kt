package com.example.models.category

import com.example.models.FeedItem

data class Category(
    override val id: Int,
    val name: String
): FeedItem