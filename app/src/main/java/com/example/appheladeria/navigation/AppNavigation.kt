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
import com.example.appheladeria.admin.ActiveProductsScreen
import com.example.appheladeria.admin.AdminDashboardScreen
import com.example.appheladeria.admin.CreateProductScreen
import com.example.appheladeria.components.AppBottomBar
import com.example.appheladeria.screens.CartScreen
import com.example.appheladeria.screens.CategoryMenuScreen
import com.example.appheladeria.screens.CustomizeScreen
import com.example.appheladeria.screens.FlavorsScreen
import com.example.appheladeria.screens.HomeScreen
import com.example.appheladeria.screens.LoginScreen
import com.example.appheladeria.screens.OrdersScreen
import com.example.appheladeria.screens.PaymentSuccessScreen
import com.example.appheladeria.screens.ProfileScreen
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

    val lastFlavor by viewModel.lastFlavor.collectAsState()
    val lastTopping by viewModel.lastTopping.collectAsState()
    val lastSize by viewModel.lastSize.collectAsState()

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

    Scaffold(
        bottomBar = {
            val hideBottomBarRoutes = listOf(
                AppScreens.Splash.route,
                AppScreens.Welcome.route,
                AppScreens.Login.route,
                AppScreens.Register.route,
                AppScreens.AdminDashboard.route,
                AppScreens.AdminActiveProducts.route,
                AppScreens.AdminCreateProduct.route
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
                        navController.navigate(AppScreens.Welcome.route) {
                            popUpTo(AppScreens.Splash.route) { inclusive = true }
                        }
                    }
                )
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
                    onLogin = { email, password ->
                        viewModel.login(email, password)
                    },
                    onGoRegister = {
                        navController.navigate(AppScreens.Register.route)
                    }
                )
            }

            composable(AppScreens.Register.route) {
                RegisterScreen(
                    onRegister = { name, email, password, phone ->
                        viewModel.register(name, email, password, phone)
                        navController.navigate(AppScreens.Home.route) {
                            popUpTo(AppScreens.Register.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.popBackStack() },
                    onGoLogin = {
                        navController.navigate(AppScreens.Login.route)
                    }
                )
            }

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
                            launchSingleTop = true
                        }
                    },
                    onGoCart = { navController.navigate(AppScreens.Cart.route) },
                    onGoProfile = { navController.navigate(AppScreens.Profile.route) },
                    onGoOrders = { navController.navigate(AppScreens.Orders.route) },
                    onGoQr = { navController.navigate(AppScreens.QrScanner.route) },
                    onGoReferral = { navController.navigate(AppScreens.Referral.route) },
                    onGoCategory = { category ->
                        navController.navigate(AppScreens.CategoryMenu.createRoute(category))
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

            composable(AppScreens.CategoryMenu.route) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""

                CategoryMenuScreen(
                    category = category,
                    onBack = { navController.popBackStack() },
                    onSelectItem = { item ->
                        viewModel.saveSelection(
                            item.name,
                            "Ninguno",
                            "M"
                        )
                        navController.navigate(AppScreens.Customize.route)
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
                    onAddToCart = { flavor, topping, size, price ->
                        viewModel.addCustomProductToCart(flavor, topping, size, price)
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
                        navController.navigate(AppScreens.Login.route) {
                            popUpTo(AppScreens.Home.route) { inclusive = true }
                            launchSingleTop = true
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
                QrScannerScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(AppScreens.Referral.route) {
                ReferralScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(AppScreens.PaymentSuccess.route) {
                PaymentSuccessScreen(
                    paidTotal = cartTotal + 5f,
                    onGoTracking = {
                        navController.navigate(AppScreens.Tracking.route)
                    },
                    onGoHome = {
                        viewModel.clearCart()
                        navController.navigate(AppScreens.Home.route) {
                            popUpTo(AppScreens.Home.route) { inclusive = true }
                            launchSingleTop = true
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
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.AdminDashboard.route) {
                AdminDashboardScreen(
                    onGoCreateProduct = {
                        navController.navigate(AppScreens.AdminCreateProduct.route)
                    }
                )
            }

            composable(AppScreens.AdminActiveProducts.route) {
                ActiveProductsScreen(
                    onBack = { navController.popBackStack() },
                    onAddNewProduct = {
                        navController.navigate(AppScreens.AdminCreateProduct.route)
                    }
                )
            }

            composable(AppScreens.AdminCreateProduct.route) {
                CreateProductScreen(
                    onBack = { navController.popBackStack() },
                    onProductCreated = {
                        navController.navigate(AppScreens.AdminDashboard.route)
                    }
                )
            }
        }
    }
}