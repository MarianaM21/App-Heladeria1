package com.example.appheladeria.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IceCreamItem(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val emoji: String = "🍨",
    val inventory: Int = 0
)