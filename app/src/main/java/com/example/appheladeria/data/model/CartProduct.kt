package com.example.appheladeria.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CartProduct(
    val flavor: String,
    val topping: String,
    val size: String,
    val price: Float,
    val quantity: Int = 1
)