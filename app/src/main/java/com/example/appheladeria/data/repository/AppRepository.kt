package com.example.appheladeria.data.repository

import com.example.appheladeria.data.datastore.DataStoreManager
import com.example.appheladeria.data.model.CartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AppRepository(
    private val dataStoreManager: DataStoreManager
) {
    fun isLoggedIn(): Flow<Boolean> = dataStoreManager.getLoginState()

    fun getUserName(): Flow<String> = dataStoreManager.getUserName()
    fun getUserEmail(): Flow<String> = dataStoreManager.getUserEmail()
    fun getUserPhone(): Flow<String> = dataStoreManager.getUserPhone()

    fun getCartItems(): Flow<List<CartProduct>> = dataStoreManager.getCartItems()

    fun getLastFlavor(): Flow<String> = dataStoreManager.getLastFlavor()
    fun getLastTopping(): Flow<String> = dataStoreManager.getLastTopping()
    fun getLastSize(): Flow<String> = dataStoreManager.getLastSize()

    suspend fun getUserEmailValue(): String = dataStoreManager.getUserEmail().first()

    suspend fun getUserPasswordValue(): String = dataStoreManager.getUserPassword().first()

    suspend fun setLoggedIn(value: Boolean) {
        dataStoreManager.saveLoginState(value)
    }

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String
    ) {
        dataStoreManager.saveUser(name, email, password, phone)
        dataStoreManager.saveLoginState(true)
    }

    suspend fun logout() {
        dataStoreManager.saveLoginState(false)
    }

    suspend fun saveCartItems(items: List<CartProduct>) {
        dataStoreManager.saveCartItems(items)
    }

    suspend fun clearCart() {
        dataStoreManager.clearCart()
    }

    suspend fun saveSelection(
        flavor: String,
        topping: String,
        size: String
    ) {
        dataStoreManager.saveLastSelection(flavor, topping, size)
    }
}