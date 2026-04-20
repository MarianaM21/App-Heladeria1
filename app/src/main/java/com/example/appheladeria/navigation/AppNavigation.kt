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
import com.example.appheladeria.screens.*
import com.example.appheladeria.viewmodel.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: AppViewModel
) {

    // ===== STATES =====
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

    // ===== LOGIN NAVIGATION =====
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

    Scaffold { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = AppScreens.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            // ===== SPLASH =====
            composable(AppScreens.Splash.route) {
                SplashScreen(
                    onFinish = {
                        navController.navigate(AppScreens.Welcome.route) {
                            popUpTo(AppScreens.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            // ===== WELCOME =====
            composable(AppScreens.Welcome.route) {
                WelcomeScreen(
                    onGoLogin = {
                        navController.navigate(AppScreens.Login.route)
                    },
                    onGoRegister = {
                        navController.navigate(AppScreens.Register.route)
                    }
                )
            }

            // ===== LOGIN =====
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

            // ===== REGISTER =====
            composable(AppScreens.Register.route) {
                RegisterScreen(
                    onRegister = { name, email, password, phone ->
                        viewModel.register(name, email, password, phone)

                        navController.navigate(AppScreens.Home.route) {
                            popUpTo(AppScreens.Register.route) { inclusive = true }
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ===== HOME =====
            composable(AppScreens.Home.route) {
                HomeScreen(
                    userName = userName,
                    cartCount = cartCount,
                    cartTotal = cartTotal,

                    onAddPromo = {
                        viewModel.addDemoProductToCart()
                    },

                    onLogout = {
                        viewModel.logout()
                        navController.navigate(AppScreens.Login.route) {
                            popUpTo(AppScreens.Home.route) { inclusive = true }
                        }
                    },

                    onGoCustomize = {
                        navController.navigate(AppScreens.Customize.route)
                    },

                    onGoCart = {
                        navController.navigate(AppScreens.Cart.route)
                    },

                    onGoProfile = {
                        navController.navigate(AppScreens.Profile.route)
                    },

                    onGoOrders = {
                        navController.navigate(AppScreens.Orders.route)
                    },

                    onGoQr = {
                        navController.navigate(AppScreens.QrScanner.route)
                    },

                    onGoReferral = {
                        navController.navigate(AppScreens.Referral.route)
                    },

                    onAddTrending = { item ->
                        viewModel.addCustomProductToCart(
                            flavor = item.name,
                            topping = "Ninguno",
                            size = "M",
                            price = item.price
                        )
                    }
                )
            }

            // ===== CUSTOMIZE =====
            composable(AppScreens.Customize.route) {
                CustomizeScreen(
                    initialFlavor = lastFlavor.ifBlank { "Vainilla" },
                    initialTopping = lastTopping.ifBlank { "Rainbow Sprinkles" },
                    initialSize = lastSize.ifBlank { "M" },

                    onBack = {
                        navController.popBackStack()
                    },

                    onGoFlavors = {
                        navController.navigate(AppScreens.Flavors.route)
                    },

                    onAddToCart = { flavor, topping, size, price ->
                        viewModel.addCustomProductToCart(
                            flavor = flavor,
                            topping = topping,
                            size = size,
                            price = price
                        )

                        navController.navigate(AppScreens.Cart.route)
                    }
                )
            }

            // ===== FLAVORS =====
            composable(AppScreens.Flavors.route) {
                FlavorsScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onSelectFlavor = { flavor ->
                        viewModel.saveSelection(
                            flavor = flavor,
                            topping = lastTopping,
                            size = lastSize
                        )
                        navController.popBackStack()
                    }
                )
            }

            // ===== CART =====
            composable(AppScreens.Cart.route) {
                CartScreen(
                    cartItems = cartItems,
                    cartCount = cartCount,
                    cartTotal = cartTotal,

                    onBack = {
                        navController.popBackStack()
                    },

                    onPayNow = {
                        navController.navigate(AppScreens.PaymentSuccess.route)
                    },

                    onRemoveItem = { index ->
                        viewModel.removeCartItem(index)
                    }
                )
            }

            // ===== PROFILE =====
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

            // ===== ORDERS =====
            composable(AppScreens.Orders.route) {
                OrdersScreen()
            }

            // ===== QR =====
            composable(AppScreens.QrScanner.route) {
                QrScannerScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ===== REFERRAL =====
            composable(AppScreens.Referral.route) {
                ReferralScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ===== PAYMENT SUCCESS =====
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
                        }
                    }
                )
            }

            // ===== TRACKING =====
            composable(AppScreens.Tracking.route) {
                TrackingScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onGoHome = {
                        navController.navigate(AppScreens.Home.route) {
                            popUpTo(AppScreens.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}