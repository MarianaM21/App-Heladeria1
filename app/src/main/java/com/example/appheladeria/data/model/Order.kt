package com.example.appheladeria.data.model

data class Order(
    val id: Int,
    val items: List<CartProduct>,
    val total: Double,
    val status: String
)