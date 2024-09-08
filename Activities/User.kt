package com.example.financepal

data class User(
    val id: Int = 0, // Default value, not required for creation
    val username: String,
    val password: String,
)
