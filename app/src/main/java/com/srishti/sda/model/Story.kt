package com.srishti.sda.model

data class Story(
    val id: String = "",
    val title: String = "",
    val story: String = "",
    val author: String = "",
    val email: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val posted: String = "",
    val likes: Int = 0,
    var isLiked: Boolean = false
) 