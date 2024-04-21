package com.example.models.tag

import com.example.models.FeedItem

data class Tag(
    override val id: Int,
    val name: String
): FeedItem