package com.srishti.sda.model

data class Category(
    val id: String,
    val name: String,
    val icon: Int,
    var isSelected: Boolean = false
) 