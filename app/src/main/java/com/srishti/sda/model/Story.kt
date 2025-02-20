package com.srishti.sda.model

data class Story(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val authorName: String = "",
    val authorEmail: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val publishDate: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    var isLiked: Boolean = false
) 