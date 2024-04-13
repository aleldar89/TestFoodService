package com.example.models_api.tag

import com.example.models_api.FeedItem

data class Tag(
    override val id: Int,
    val name: String
): FeedItem