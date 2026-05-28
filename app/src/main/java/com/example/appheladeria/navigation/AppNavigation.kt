package com.example.appheladeria.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appheladeria.admin.ActiveProductsScreen
import com.example.appheladeria.admin.AdminDashboardScreen
import com.example.appheladeria.admin.CreateProductScreen
import com.example.appheladeria.admin.SalesHistoryScreen
import com.example.appheladeria.components.AppBottomBar
import com.example.appheladeria.data.model.Notification
import com.example.appheladeria.screens.CartScreen
import com.example.appheladeria.screens.CategoryMenuScreen
import com.example.appheladeria.screens.CustomizeScreen
import com.example.appheladeria.screens.FlavorsScreen
import com.example.appheladeria.screens.HomeScreen
import com.example.appheladeria.screens.LoginScreen
import com.example.appheladeria.screens.NotificationsScreen
import com.example.appheladeria.screens.OrdersScreen
import com.example.appheladeria.screens.PaymentSuccessScreen
import com.example.appheladeria.screens.PhotoBoothScreen
import com.example.appheladeria.screens.ProfileScreen
import com.example.appheladeria.screens.PromotionsScreen
import com.example.appheladeria.screens.QrScannerScreen
import com.example.appheladeria.screens.ReferralScreen
import com.example.appheladeria.screens.RegisterScreen
import com.example.appheladeria.screens.SplashScreen
import com.example.appheladeria.screens.TrackingScreen
import com.example.appheladeria.screens.WelcomeScreen
import com.example.appheladeria.viewmodel.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val isLoggingIn by viewModel.isLoggingIn.collectAsState()

    val userName by viewModel.userName.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()
    val userPhone by viewModel.userPhone.collectAsState()

    val cartItems by viewModel.cartItems.collectAsState()
    val cartCount by viewModel.cartCount.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val products by viewModel.products.collectAsState()

    val lastFlavor by viewModel.lastFlavor.collectAsState()
    val lastTopping by viewModel.lastTopping.collectAsState()
    val lastSize by viewModel.lastSize.collectAsState()

    val notifications = remember {
        mutableStateListOf(
            Notification(
                id = "obligatoria_1",
                title = "Notificación obligatoria",
                message = "Antes de finalizar tu compra, revisa el sabor, tamaño, topping y total del pedido.",
                timestamp = System.currentTimeMillis(),
                isRead = false
            ),
            Notification(
                id = "bienvenida_1",
                title = "Bienvenido a Scoop & Smile",
                message = "Gracias por ingresar. Explora sabores, promociones y pedidos recientes.",
                timestamp = System.currentTimeMillis() - 60000,
                isRead = false
            ),
            Notification(
                id = "promo_1",
                title = "Promoción secreta disponible",
                message = "Escanea el código QR promocional para desbloquear ofertas especiales.",
                timestamp = System.currentTimeMillis() - 120000,
                isRead = false
            ),
            Notification(
                id = "spot_1",
                title = "Spot de fotos activo",
                message = "Tómate una foto con tu helado, guárdala y compártela con tus amigos.",
                timestamp = System.currentTimeMillis() - 180000,
                isRead = false
            )
        )
    }

    val unreadNotifications =
        notifications.count { !it.isRead }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            delay(1200)

            if (userEmail == "admin@heladeria.com") {
                navController.navigate(
                    AppScreens.AdminDashboard.route
                ) {
                    popUpTo(AppScreens.Login.route) {
                        inclusive = true
                    }

                    launchSingleTop = true
                }
            } else {
                navController.navigate(
                    AppScreens.Home.route
                ) {
                    popUpTo(AppScreens.Login.route) {
                        inclusive = true
                    }

                    launchSingleTop = true
                }
            }

            viewModel.resetLoginState()
        }
    }

    Scaffold(
        bottomBar = {
            val hideBottomBarRoutes = listOf(
                AppScreens.Splash.route,
                AppScreens.Welcome.route,
                AppScreens.Login.route,
                AppScreens.Register.route,
                AppScreens.AdminDashboard.route,
                AppScreens.AdminActiveProducts.route,
                AppScreens.AdminCreateProduct.route,
                AppScreens.AdminSalesHistory.route,
                AppScreens.AdminConfirmation.route,
                AppScreens.PhotoBooth.route,
                AppScreens.Promotions.route,
                AppScreens.Notifications.route
            )

            if (currentRoute !in hideBottomBarRoutes) {
                AppBottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = AppScreens.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(AppScreens.Splash.route) {
                SplashScreen(
                    onFinish = {
                        navController.navigate(
                            AppScreens.Welcome.route
                        ) {
                            popUpTo(AppScreens.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(AppScreens.Welcome.route) {
                WelcomeScreen(
                    onGoLogin = {
                        navController.navigate(
                            AppScreens.Login.route
                        )
                    },

                    onGoRegister = {
                        navController.navigate(
                            AppScreens.Register.route
                        )
                    }
                )
            }

            composable(AppScreens.Login.route) {
                LoginScreen(
                    loginError = loginError,
                    isLoggingIn = isLoggingIn,

                    onLogin = { email, password ->
                        viewModel.login(email, password)
                    },

                    onGoRegister = {
                        navController.navigate(
                            AppScreens.Register.route
                        )
                    }
                )
            }

            composable(AppScreens.Register.route) {
                RegisterScreen(
                    onRegister = { name, email, password, phone ->
                        viewModel.register(
                            name,
                            email,
                            password,
                            phone
                        )

                        navController.navigate(
                            AppScreens.Home.route
                        ) {
                            popUpTo(AppScreens.Register.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    },

                    onBack = {
                        navController.popBackStack()
                    },

                    onGoLogin = {
                        navController.navigate(
                            AppScreens.Login.route
                        )
                    }
                )
            }

            composable(AppScreens.Home.route) {
                HomeScreen(
                    userName = userName,
                    cartCount = cartCount,
                    cartTotal = cartTotal,
                    unreadNotifications = unreadNotifications,

                    onAddPromo = {
                        viewModel.addDemoProductToCart()
                    },

                    onLogout = {
                        viewModel.logout()

                        navController.navigate(
                            AppScreens.Login.route
                        ) {
                            popUpTo(AppScreens.Home.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    },

                    onGoCart = {
                        navController.navigate(
                            AppScreens.Cart.route
                        )
                    },

                    onGoProfile = {
                        navController.navigate(
                            AppScreens.Profile.route
                        )
                    },

                    onGoOrders = {
                        navController.navigate(
                            AppScreens.Orders.route
                        )
                    },

                    onGoQr = {
                        navController.navigate(
                            AppScreens.QrScanner.route
                        )
                    },

                    onGoPhotoBooth = {
                        navController.navigate(
                            AppScreens.PhotoBooth.route
                        )
                    },

                    onGoReferral = {
                        navController.navigate(
                            AppScreens.Referral.route
                        )
                    },

                    onGoNotifications = {
                        navController.navigate(
                            AppScreens.Notifications.route
                        )
                    },

                    onGoCategory = { category ->
                        navController.navigate(
                            AppScreens.CategoryMenu.createRoute(category)
                        )
                    },

                    onAddTrending = { item ->
                        viewModel.addCustomProductToCart(
                            item.name,
                            "Ninguno",
                            "M",
                            item.price
                        )
                    }
                )
            }

            composable(AppScreens.Notifications.route) {
                NotificationsScreen(
                    notifications = notifications,

                    onBack = {
                        navController.popBackStack()
                    },

                    onMarkAsRead = {
                        for (index in notifications.indices) {
                            notifications[index] =
                                notifications[index].copy(
                                    isRead = true
                                )
                        }
                    }
                )
            }

            composable(AppScreens.CategoryMenu.route) { backStackEntry ->

                val category =
                    backStackEntry.arguments
                        ?.getString("category") ?: ""

                CategoryMenuScreen(
                    category = category,

                    onBack = {
                        navController.popBackStack()
                    },

                    onSelectItem = { item ->
                        viewModel.saveSelection(
                            item.name,
                            "Ninguno",
                            "M"
                        )

                        navController.navigate(
                            AppScreens.Customize.route
                        )
                    }
                )
            }

            composable(AppScreens.Customize.route) {
                CustomizeScreen(
                    initialFlavor =
                        lastFlavor.ifBlank { "Vainilla" },

                    initialTopping =
                        lastTopping.ifBlank { "Rainbow Sprinkles" },

                    initialSize =
                        lastSize.ifBlank { "M" },

                    onBack = {
                        navController.popBackStack()
                    },

                    onGoFlavors = {
                        navController.navigate(
                            AppScreens.Flavors.route
                        )
                    },

                    onAddToCart = { flavor, topping, size, price ->
                        viewModel.addCustomProductToCart(
                            flavor,
                            topping,
                            size,
                            price
                        )

                        navController.navigate(
                            AppScreens.Cart.route
                        )
                    }
                )
            }

            composable(AppScreens.Flavors.route) {
                FlavorsScreen(
                    onBack = {
                        navController.popBackStack()
                    },

                    onSelectFlavor = { flavor ->
                        viewModel.saveSelection(
                            flavor,
                            lastTopping,
                            lastSize
                        )

                        navController.popBackStack()
                    }
                )
            }

            composable(AppScreens.Cart.route) {
                CartScreen(
                    cartItems = cartItems,
                    cartCount = cartCount,
                    cartTotal = cartTotal,

                    onBack = {
                        navController.popBackStack()
                    },

                    onPayNow = {
                        viewModel.createSampleOrder()

                        navController.navigate(
                            AppScreens.PaymentSuccess.route
                        )
                    },

                    onRemoveItem = { index ->
                        viewModel.removeCartItem(index)
                    }
                )
            }

            composable(AppScreens.Profile.route) {
                ProfileScreen(
                    userName = userName,
                    userEmail = userEmail,
                    userPhone = userPhone,

                    onLogout = {
                        viewModel.logout()

                        navController.navigate(
                            AppScreens.Login.route
                        ) {
                            popUpTo(AppScreens.Home.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.Orders.route) {
                OrdersScreen(
                    orders = orders,

                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(AppScreens.QrScanner.route) {
                QrScannerScreen(
                    onBack = {
                        navController.popBackStack()
                    },

                    onPromoDetected = {
                        navController.navigate(
                            AppScreens.Promotions.route
                        ) {
                            popUpTo(AppScreens.QrScanner.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.Promotions.route) {
                PromotionsScreen(
                    onBack = {
                        navController.navigate(
                            AppScreens.Home.route
                        ) {
                            launchSingleTop = true
                        }
                    },

                    onAddPromotion = { promotion ->
                        viewModel.addCustomProductToCart(
                            promotion.name,
                            "Promoción",
                            promotion.tag,
                            promotion.price
                        )
                    },

                    onGoCart = {
                        navController.navigate(
                            AppScreens.Cart.route
                        )
                    }
                )
            }

            composable(AppScreens.Referral.route) {
                ReferralScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(AppScreens.PhotoBooth.route) {
                PhotoBoothScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(AppScreens.PaymentSuccess.route) {
                PaymentSuccessScreen(
                    paidTotal = cartTotal + 5f,

                    onGoTracking = {
                        navController.navigate(
                            AppScreens.Tracking.route
                        )
                    },

                    onGoHome = {
                        viewModel.clearCart()

                        navController.navigate(
                            AppScreens.Home.route
                        ) {
                            popUpTo(AppScreens.Home.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.Tracking.route) {
                TrackingScreen(
                    onBack = {
                        navController.popBackStack()
                    },

                    onGoHome = {
                        navController.navigate(
                            AppScreens.Home.route
                        ) {
                            popUpTo(AppScreens.Home.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.AdminDashboard.route) {
                AdminDashboardScreen(
                    products = products,
                    onGoCreateProduct = {
                        navController.navigate(
                            AppScreens.AdminCreateProduct.route
                        )
                    },
                    onGoInventory = {
                        navController.navigate(
                            AppScreens.AdminActiveProducts.route
                        )
                    },
                    onGoOrders = {
                        navController.navigate(
                            AppScreens.AdminSalesHistory.route
                        )
                    },
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(AppScreens.Login.route) {
                            popUpTo(0)
                        }
                    }
                )
            }

            composable(AppScreens.AdminActiveProducts.route) {
                ActiveProductsScreen(
                    productsList = products,
                    onBack = {
                        navController.popBackStack()
                    },
                    onAddNewProduct = {
                        navController.navigate(
                            AppScreens.AdminCreateProduct.route
                        )
                    },
                    onGoDashboard = {
                        navController.navigate(AppScreens.AdminDashboard.route) {
                            popUpTo(AppScreens.AdminDashboard.route) { inclusive = true }
                        }
                    },
                    onGoOrders = {
                        navController.navigate(AppScreens.AdminSalesHistory.route)
                    },
                    onDeleteProduct = { product ->
                         viewModel.removeProduct(product.id)
                    }
                )
            }

            composable(AppScreens.AdminCreateProduct.route) {
                CreateProductScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onProductCreated = { name, stock, price ->
                        viewModel.addProduct(name, price.toDouble(), "Helados", stock)
                        navController.navigate(AppScreens.AdminDashboard.route) {
                            popUpTo(AppScreens.AdminDashboard.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppScreens.AdminSalesHistory.route) {
                SalesHistoryScreen(
                    orders = orders,
                    onBack = {
                        navController.popBackStack()
                    },
                    onGoInventory = {
                        navController.navigate(AppScreens.AdminActiveProducts.route)
                    },
                    onGoDashboard = {
                        navController.navigate(AppScreens.AdminDashboard.route) {
                            popUpTo(AppScreens.AdminDashboard.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppScreens.AdminConfirmation.route) {
                AdminPlaceholderScreen(
                    title = "Confirmación",
                    message = "Pantalla de confirmación administrativa.",
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun AdminPlaceholderScreen(
    title: String,
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = message
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack
        ) {
            Text("Volver")
        }
    }
}