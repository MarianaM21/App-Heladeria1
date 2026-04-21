package com.example.appheladeria.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appheladeria.screens.*
import com.example.appheladeria.admin.*
import com.example.appheladeria.components.AppBottomBar
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

    val lastFlavor by viewModel.lastFlavor.collectAsState()
    val lastTopping by viewModel.lastTopping.collectAsState()
    val lastSize by viewModel.lastSize.collectAsState()

    // Login Navegation
    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            delay(1200)
            navController.navigate(AppScreens.Home.route) {
                popUpTo(AppScreens.Login.route) { inclusive = true }
                launchSingleTop = true
            }
            viewModel.resetLoginState()
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != AppScreens.Splash.route) {
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

            // Splash
            composable(AppScreens.Splash.route) {
                SplashScreen(
                    onFinish = {
                        navController.navigate(AppScreens.Welcome.route) {
                            popUpTo(AppScreens.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            // Welcome
            composable(AppScreens.Welcome.route) {
                WelcomeScreen(
                    onGoLogin = { navController.navigate(AppScreens.Login.route) },
                    onGoRegister = { navController.navigate(AppScreens.Register.route) }
                )
            }

            // Login
            composable(AppScreens.Login.route) {
                LoginScreen(
                    loginError = loginError,
                    isLoggingIn = isLoggingIn,
                    onLogin = { email, password -> viewModel.login(email, password) },
                    onGoRegister = { navController.navigate(AppScreens.Register.route) }
                )
            }

            // Registro
            composable(AppScreens.Register.route) {
                RegisterScreen(
                    onRegister = { n, e, p, ph -> 
                        viewModel.register(n, e, p, ph)
                        navController.navigate(AppScreens.Home.route) {
                            popUpTo(AppScreens.Register.route) { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() },
                    onGoLogin = { navController.navigate(AppScreens.Login.route) }
                )
            }

            // Home
            composable(AppScreens.Home.route) {
                HomeScreen(
                    userName = userName,
                    cartCount = cartCount,
                    cartTotal = cartTotal,
                    onAddPromo = { viewModel.addDemoProductToCart() },
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(AppScreens.Login.route) {
                            popUpTo(AppScreens.Home.route) { inclusive = true }
                        }
                    },
                    onGoCustomize = { navController.navigate(AppScreens.Customize.route) },
                    onGoCart = { navController.navigate(AppScreens.Cart.route) },
                    onGoProfile = { navController.navigate(AppScreens.Profile.route) },
                    onGoOrders = { navController.navigate(AppScreens.Orders.route) },
                    onGoQr = { navController.navigate(AppScreens.QrScanner.route) },
                    onGoReferral = { navController.navigate(AppScreens.Referral.route) },
                    onAddTrending = { item ->
                        viewModel.addCustomProductToCart(item.name, "Ninguno", "M", item.price)
                    }
                )
            }

            // Customize
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

            //Flavors
            composable(AppScreens.Flavors.route) {
                FlavorsScreen(
                    onBack = { navController.popBackStack() },
                    onSelectFlavor = { flavor ->
                        viewModel.saveSelection(flavor, lastTopping, lastSize)
                        navController.popBackStack()
                    }
                )
            }

            // Cart
            composable(AppScreens.Cart.route) {
                CartScreen(
                    cartItems = cartItems,
                    cartCount = cartCount,
                    cartTotal = cartTotal,
                    onBack = { navController.popBackStack() },
                    onPayNow = { navController.navigate(AppScreens.PaymentSuccess.route) },
                    onRemoveItem = { index -> viewModel.removeCartItem(index) }
                )
            }

            // Profile
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

            // ORDERS
            composable(AppScreens.Orders.route) {
                OrdersScreen(onBack = { navController.popBackStack() })
            }

            // QR
            composable(AppScreens.QrScanner.route) {
                QrScannerScreen(onBack = { navController.popBackStack() })
            }

            //Referral
            composable(AppScreens.Referral.route) {
                ReferralScreen(onBack = { navController.popBackStack() })
            }

            // PaymentSuccess
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

            //Tracking
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
            //Admin
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
                    onProductCreated = { navController.popBackStack() }
                )
            }
        }
    }
}
