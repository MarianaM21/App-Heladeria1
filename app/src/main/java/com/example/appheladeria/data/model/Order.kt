package com.example.appheladeria.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable

data class Order(
    val id: Int,
    val items: List<CartProduct>,
    val total: Double,
    val status: String
)