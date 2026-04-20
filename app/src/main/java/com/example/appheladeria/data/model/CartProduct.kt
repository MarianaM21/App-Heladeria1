package com.example.appheladeria.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartProduct(
    val flavor: String,
    val topping: String,
    val size: String,
    val price: Float,
    val quantity: Int = 1
)