package com.example.appheladeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appheladeria.data.model.CartProduct
import com.example.appheladeria.data.model.Notification
import com.example.appheladeria.data.model.Order
import com.example.appheladeria.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AppViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val ADMIN_EMAIL = "admin@heladeria.com"
    private val ADMIN_PASS = "admin123"

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

    val orders = repository.getOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications = repository.getNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationsCount = repository.getNotifications()
        .map { list -> list.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

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

    private val _topNotification = MutableStateFlow<Notification?>(null)
    val topNotification: StateFlow<Notification?> = _topNotification.asStateFlow()

    fun register(name: String, email: String, pass: String, phone: String) {
        viewModelScope.launch {
            repository.registerUser(name, email, pass, phone)
            addNotification("Registro exitoso", "Bienvenido a Heladería App, $name")
        }
    }

    fun login(email: String, password: String) {
        if (_isLoggingIn.value) return
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = ""
            _loginSuccess.value = null
            delay(800)

            if (email.trim() == ADMIN_EMAIL && password.trim() == ADMIN_PASS) {
                repository.setLoggedIn(true)
                repository.registerUser("Administrador", ADMIN_EMAIL, ADMIN_PASS, "000000")
                _loginSuccess.value = true
                addNotification("Sesión iniciada", "Bienvenido, Administrador")
                _isLoggingIn.value = false
                return@launch
            }

            val savedEmail = repository.getUserEmailValue().trim()
            val savedPassword = repository.getUserPasswordValue().trim()

            when {
                email.isBlank() || password.isBlank() -> {
                    _loginError.value = "Todos los campos son obligatorios"
                    _loginSuccess.value = false
                }
                savedEmail.isBlank() -> {
                    _loginError.value = "Primero debes crear una cuenta"
                    _loginSuccess.value = false
                }
                email.trim() != savedEmail -> {
                    _loginError.value = "Correo incorrecto"
                    _loginSuccess.value = false
                }
                password.trim() != savedPassword -> {
                    _loginError.value = "Contraseña incorrecta"
                    _loginSuccess.value = false
                }
                else -> {
                    repository.setLoggedIn(true)
                    _loginSuccess.value = true
                    addNotification("Sesión iniciada", "¡Bienvenid@ de nuevo!")
                }
            }
            _isLoggingIn.value = false
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
            addNotification("Sesión cerrada", "Has cerrado sesión correctamente")
        }
    }

    fun addNotification(title: String, message: String) {
        viewModelScope.launch {
            val current = repository.getNotificationsValue().toMutableList()
            val newNotif = Notification(
                id = UUID.randomUUID().toString(),
                title = title,
                message = message,
                timestamp = System.currentTimeMillis()
            )
            current.add(0, newNotif)
            repository.saveNotifications(current)
            _topNotification.value = newNotif
            delay(5000)
            if (_topNotification.value?.id == newNotif.id) {
                _topNotification.value = null
            }
        }
    }

    fun dismissTopNotification() { _topNotification.value = null }

    fun markNotificationsAsRead() {
        viewModelScope.launch {
            val current = repository.getNotificationsValue().map { it.copy(isRead = true) }
            repository.saveNotifications(current)
        }
    }

    fun createSampleOrder() {
        viewModelScope.launch {
            val currentItems = cartItems.value
            if (currentItems.isEmpty()) return@launch
            val currentOrders = repository.getOrdersValue().toMutableList()
            val newOrder = Order(
                id = currentOrders.size + 1,
                items = currentItems,
                total = currentItems.sumOf { (it.price * it.quantity).toDouble() },
                status = "En camino"
            )
            currentOrders.add(0, newOrder)
            repository.saveOrders(currentOrders)
            clearCart()
            addNotification("¡Pedido realizado!", "Tu pedido #${newOrder.id} está siendo preparado.")
        }
    }

    fun addDemoProductToCart() {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()
            val newProduct = CartProduct("Waffle Cones Promo", "Promo", "2x1", 5.50f, 1)
            current.add(newProduct)
            repository.saveCartItems(current)
            addNotification("¡Producto añadido!", "Se ha agregado 2x1 Waffle Cones al carrito.")
        }
    }

    fun addCustomProductToCart(flavor: String, topping: String, size: String, price: Float) {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()
            val newProduct = CartProduct(flavor, topping, size, price, 1)
            current.add(newProduct)
            repository.saveCartItems(current)
            repository.saveSelection(flavor, topping, size)
            addNotification("Nuevo sabor", "Añadiste $flavor a tu carrito.")
        }
    }

    fun removeCartItem(index: Int) {
        viewModelScope.launch {
            val current = cartItems.value.toMutableList()
            if (index in current.indices) {
                current.removeAt(index)
                repository.saveCartItems(current)
            }
        }
    }

    fun clearCart() { viewModelScope.launch { repository.clearCart() } }
    fun saveSelection(f: String, t: String, s: String) { viewModelScope.launch { repository.saveSelection(f, t, s) } }
}
