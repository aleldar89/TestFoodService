package com.example.models_api.category

import com.example.models_api.FeedItem

data class Category(
    override val id: Int,
    val name: String
): FeedItem