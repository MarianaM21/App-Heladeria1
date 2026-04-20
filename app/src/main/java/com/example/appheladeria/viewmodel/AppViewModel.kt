package com.example.appheladeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appheladeria.data.model.CartProduct
import com.example.appheladeria.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val repository: AppRepository
) : ViewModel() {

    val isLoggedIn = repository.isLoggedIn()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val userName = repository.getUserName()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userEmail = repository.getUserEmail()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userPhone = repository.getUserPhone()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val cartItems = repository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartCount = repository.getCartItems()
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val cartTotal = repository.getCartItems()
        .map { items -> items.sumOf { (it.price * it.quantity).toDouble() }.toFloat() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    val lastFlavor = repository.getLastFlavor()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val lastTopping = repository.getLastTopping()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val lastSize = repository.getLastSize()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess.asStateFlow()

    private val _loginError = MutableStateFlow("")
    val loginError: StateFlow<String> = _loginError.asStateFlow()

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        phone: String
    ) {
        viewModelScope.launch {
            repository.registerUser(name, email, password, phone)
        }
    }

    fun login(email: String, password: String) {
        if (_isLoggingIn.value) return

        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = ""
            _loginSuccess.value = null

            delay(700)

            val savedEmail = repository.getUserEmailValue().trim()
            val savedPassword = repository.getUserPasswordValue().trim()

            when {
                email.isBlank() || password.isBlank() -> {
                    _loginSuccess.value = false
                    _loginError.value = "Todos los campos son obligatorios"
                    _isLoggingIn.value = false
                }

                savedEmail.isBlank() || savedPassword.isBlank() -> {
                    _loginSuccess.value = false
                    _loginError.value = "Primero debes crear una cuenta"
                    _isLoggingIn.value = false
                }

                email.trim() != savedEmail -> {
                    _loginSuccess.value = false
                    _loginError.value = "Correo incorrecto"
                    _isLoggingIn.value = false
                }

                password.trim() != savedPassword -> {
                    _loginSuccess.value = false
                    _loginError.value = "Contraseña incorrecta"
                    _isLoggingIn.value = false
                }

                else -> {
                    repository.setLoggedIn(true)
                    _loginError.value = ""
                    _loginSuccess.value = true
                }
            }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = null
        _loginError.value = ""
        _isLoggingIn.value = false
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun addDemoProductToCart() {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()

            val newProduct = CartProduct(
                flavor = "Waffle Cones Promo",
                topping = "Promo",
                size = "2x1",
                price = 5.50f,
                quantity = 1
            )

            val existingIndex = current.indexOfFirst {
                it.flavor == newProduct.flavor &&
                        it.topping == newProduct.topping &&
                        it.size == newProduct.size
            }

            if (existingIndex >= 0) {
                val existing = current[existingIndex]
                current[existingIndex] = existing.copy(
                    quantity = existing.quantity + 1
                )
            } else {
                current.add(newProduct)
            }

            repository.saveCartItems(current)
        }
    }

    fun addCustomProductToCart(
        flavor: String,
        topping: String,
        size: String,
        price: Float
    ) {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()

            val newProduct = CartProduct(
                flavor = flavor,
                topping = topping,
                size = size,
                price = price,
                quantity = 1
            )

            val existingIndex = current.indexOfFirst {
                it.flavor == newProduct.flavor &&
                        it.topping == newProduct.topping &&
                        it.size == newProduct.size
            }

            if (existingIndex >= 0) {
                val existing = current[existingIndex]
                current[existingIndex] = existing.copy(
                    quantity = existing.quantity + 1
                )
            } else {
                current.add(newProduct)
            }

            repository.saveCartItems(current)
            repository.saveSelection(flavor, topping, size)
        }
    }

    fun removeCartItem(index: Int) {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()

            if (index in current.indices) {
                val item = current[index]

                if (item.quantity > 1) {
                    current[index] = item.copy(quantity = item.quantity - 1)
                } else {
                    current.removeAt(index)
                }

                repository.saveCartItems(current)
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun saveSelection(
        flavor: String,
        topping: String,
        size: String
    ) {
        viewModelScope.launch {
            repository.saveSelection(flavor, topping, size)
        }
    }
}