package com.example.book_recommendations_app_group1

data class Book(
    val id: Long,
    val name: String,
    val author: String,
    val description: String,
    val genre: String,
    val readingLevel: String,
    val imageResourceId: String
)
