package com.example.glamup

data class Review(
    val beautyProId: String = "",
    val userId: String = "",
    val userName: String = "",
    val rating: Float = 0f,
    val text: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
