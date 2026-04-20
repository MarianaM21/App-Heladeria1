package com.example.appheladeria.data.model

data class IceCreamItem(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val emoji: String = "🍨"
)