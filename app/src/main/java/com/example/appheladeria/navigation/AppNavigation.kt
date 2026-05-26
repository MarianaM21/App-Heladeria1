package com.example.appheladeria.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appheladeria.screens.*
import com.example.appheladeria.admin.*
import com.example.appheladeria.components.AppBottomBar
import com.example.appheladeria.viewmodel.AppViewModel
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
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

    val lastFlavor by viewModel.lastFlavor.collectAsState()
    val lastTopping by viewModel.lastTopping.collectAsState()
    val lastSize by viewModel.lastSize.collectAsState()

    // Estados adicionales
    val orders by viewModel.orders.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val topNotification by viewModel.topNotification.collectAsState()
    val unreadNotificationsCount by viewModel.unreadNotificationsCount.collectAsState()

    // Login Navegation
    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            delay(1200)
            if (userEmail == "admin@heladeria.com") {
                navController.navigate(AppScreens.AdminDashboard.route) {
                    popUpTo(AppScreens.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                navController.navigate(AppScreens.Home.route) {
                    popUpTo(AppScreens.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
            viewModel.resetLoginState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // ===== ALERTA SUPERIOR (TOP NOTIFICATION) =====
        AnimatedVisibility(
            visible = topNotification != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(16.dp)
                .zIndex(100f)
        ) {
            topNotification?.let { notif ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = PrimaryPink,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = notif.title, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 14.sp)
                            Text(text = notif.message, color = TextDark.copy(alpha = 0.7f), fontSize = 12.sp)
                        }
                    }
                }
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
                    AppScreens.AdminConfirmation.route
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
                    SplashScreen(onFinish = {
                        navController.navigate(AppScreens.Welcome.route) {
                            popUpTo(AppScreens.Splash.route) { inclusive = true }
                        }
                    })
                }

                composable(AppScreens.Welcome.route) {
                    WelcomeScreen(
                        onGoLogin = { navController.navigate(AppScreens.Login.route) },
                        onGoRegister = { navController.navigate(AppScreens.Register.route) }
                    )
                }

                composable(AppScreens.Login.route) {
                    LoginScreen(
                        loginError = loginError,
                        isLoggingIn = isLoggingIn,
                        onLogin = { email, password -> viewModel.login(email, password) },
                        onGoRegister = { navController.navigate(AppScreens.Register.route) }
                    )
                }

                composable(AppScreens.Register.route) {
                    RegisterScreen(
                        onRegister = { n, e, p, ph -> 
                            viewModel.register(n, e, p, ph)
                        },
                        onBack = { navController.popBackStack() },
                        onGoLogin = { navController.navigate(AppScreens.Login.route) }
                    )
                }

                composable(AppScreens.Home.route) {
                    HomeScreen(
                        userName = userName,
                        cartCount = cartCount,
                        cartTotal = cartTotal,
                        unreadNotifications = unreadNotificationsCount,
                        onAddPromo = { viewModel.addDemoProductToCart() },
                        onLogout = {
                            viewModel.logout()
                            navController.navigate(AppScreens.Login.route) {
                                popUpTo(AppScreens.Home.route) { inclusive = true }
                            }
                        },
                        onGoCart = { navController.navigate(AppScreens.Cart.route) },
                        onGoProfile = { navController.navigate(AppScreens.Profile.route) },
                        onGoOrders = { navController.navigate(AppScreens.Orders.route) },
                        onGoQr = { navController.navigate(AppScreens.QrScanner.route) },
                        onGoReferral = { navController.navigate(AppScreens.Referral.route) },
                        onGoNotifications = { navController.navigate(AppScreens.Notifications.route) },
                        onGoCategory = { /* Acción para categorías */ },
                        onAddTrending = { item ->
                            viewModel.addCustomProductToCart(item.name, "Ninguno", "M", item.price)
                        }
                    )
                }

                composable(AppScreens.Customize.route) {
                    CustomizeScreen(
                        initialFlavor = lastFlavor.ifBlank { "Vainilla" },
                        initialTopping = lastTopping.ifBlank { "Rainbow Sprinkles" },
                        initialSize = lastSize.ifBlank { "M" },
                        onBack = { navController.popBackStack() },
                        onGoFlavors = { navController.navigate(AppScreens.Flavors.route) },
                        onAddToCart = { f, t, s, p ->
                            viewModel.addCustomProductToCart(f, t, s, p)
                            navController.navigate(AppScreens.Cart.route)
                        }
                    )
                }

                composable(AppScreens.Flavors.route) {
                    FlavorsScreen(
                        onBack = { navController.popBackStack() },
                        onSelectFlavor = { flavor ->
                            viewModel.saveSelection(flavor, lastTopping, lastSize)
                            navController.popBackStack()
                        }
                    )
                }

                composable(AppScreens.Cart.route) {
                    CartScreen(
                        cartItems = cartItems,
                        cartCount = cartCount,
                        cartTotal = cartTotal,
                        onBack = { navController.popBackStack() },
                        onPayNow = { 
                            viewModel.createSampleOrder()
                            navController.navigate(AppScreens.PaymentSuccess.route) 
                        },
                        onRemoveItem = { index -> viewModel.removeCartItem(index) }
                    )
                }

                composable(AppScreens.Profile.route) {
                    ProfileScreen(
                        userName = userName,
                        userEmail = userEmail,
                        userPhone = userPhone,
                        onLogout = {
                            viewModel.logout()
                            navController.navigate(AppScreens.Login.route) {
                                popUpTo(AppScreens.Home.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(AppScreens.Orders.route) {
                    OrdersScreen(
                        orders = orders,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(AppScreens.QrScanner.route) {
                    QrScannerScreen(onBack = { navController.popBackStack() })
                }

                composable(AppScreens.Referral.route) {
                    ReferralScreen(onBack = { navController.popBackStack() })
                }

                composable(AppScreens.Notifications.route) {
                    NotificationsScreen(
                        notifications = notifications,
                        onBack = { navController.popBackStack() },
                        onMarkAsRead = { viewModel.markNotificationsAsRead() }
                    )
                }

                composable(AppScreens.PaymentSuccess.route) {
                    PaymentSuccessScreen(
                        paidTotal = cartTotal + 5f,
                        onGoTracking = { navController.navigate(AppScreens.Tracking.route) },
                        onGoHome = {
                            viewModel.clearCart()
                            navController.navigate(AppScreens.Home.route) {
                                popUpTo(AppScreens.Home.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(AppScreens.Tracking.route) {
                    TrackingScreen(
                        onBack = { navController.popBackStack() },
                        onGoHome = {
                            navController.navigate(AppScreens.Home.route) {
                                popUpTo(AppScreens.Home.route) { inclusive = true }
                            }
                        }
                    )
                }
                
                // Admin
                composable(AppScreens.AdminDashboard.route) {
                    AdminDashboardScreen(onGoCreateProduct = { navController.navigate(AppScreens.AdminCreateProduct.route) })
                }
                composable(AppScreens.AdminActiveProducts.route) {
                    ActiveProductsScreen(
                        onBack = { navController.popBackStack() },
                        onAddNewProduct = { navController.navigate(AppScreens.AdminCreateProduct.route) }
                    )
                }
                composable(AppScreens.AdminCreateProduct.route) {
                    CreateProductScreen(
                        onBack = { navController.popBackStack() },
                        onProductCreated = { navController.navigate(AppScreens.AdminConfirmation.route) }
                    )
                }
                composable(AppScreens.AdminSalesHistory.route) {
                    SalesHistoryScreen(onBack = { navController.popBackStack() })
                }
                composable(AppScreens.AdminConfirmation.route) {
                    ProductConfirmationScreen(
                        onBack = { navController.popBackStack() },
                        onGoDashboard = { 
                            navController.navigate(AppScreens.AdminDashboard.route) {
                                popUpTo(AppScreens.AdminDashboard.route) { inclusive = true }
                            }
                        },
                        onAddAnother = {
                            navController.navigate(AppScreens.AdminCreateProduct.route) {
                                popUpTo(AppScreens.AdminConfirmation.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
