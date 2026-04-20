package com.example.appheladeria.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.appheladeria.data.model.CartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

private const val DATASTORE_NAME = "app_heladeria_prefs"
private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreManager(private val context: Context) {

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_PHONE = stringPreferencesKey("user_phone")

        val CART_ITEMS = stringPreferencesKey("cart_items_json")

        val LAST_FLAVOR = stringPreferencesKey("last_flavor")
        val LAST_TOPPING = stringPreferencesKey("last_topping")
        val LAST_SIZE = stringPreferencesKey("last_size")
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun saveLoginState(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }

    fun getLoginState(): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    suspend fun saveUser(
        name: String,
        email: String,
        password: String,
        phone: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
            preferences[USER_PASSWORD] = password
            preferences[USER_PHONE] = phone
        }
    }

    fun getUserName(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    fun getUserEmail(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL] ?: ""
        }

    fun getUserPassword(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_PASSWORD] ?: ""
        }

    fun getUserPhone(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_PHONE] ?: ""
        }

    suspend fun saveCartItems(items: List<CartProduct>) {
        context.dataStore.edit { preferences ->
            preferences[CART_ITEMS] = json.encodeToString(items)
        }
    }

    fun getCartItems(): Flow<List<CartProduct>> =
        context.dataStore.data.map { preferences ->
            val raw = preferences[CART_ITEMS] ?: "[]"
            try {
                json.decodeFromString<List<CartProduct>>(raw)
            } catch (_: Exception) {
                emptyList()
            }
        }

    suspend fun saveLastSelection(
        flavor: String,
        topping: String,
        size: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[LAST_FLAVOR] = flavor
            preferences[LAST_TOPPING] = topping
            preferences[LAST_SIZE] = size
        }
    }

    fun getLastFlavor(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[LAST_FLAVOR] ?: ""
        }

    fun getLastTopping(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[LAST_TOPPING] ?: ""
        }

    fun getLastSize(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[LAST_SIZE] ?: ""
        }

    suspend fun clearCart() {
        context.dataStore.edit { preferences ->
            preferences[CART_ITEMS] = "[]"
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}