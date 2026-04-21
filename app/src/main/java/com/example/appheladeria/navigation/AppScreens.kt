package com.example.appheladeria.navigation

sealed class AppScreens(val route: String) {
    data object Splash : AppScreens("splash")
    data object Welcome : AppScreens("welcome")
    data object Login : AppScreens("login")
    data object Register : AppScreens("register")
    data object Home : AppScreens("home")
    data object Customize : AppScreens("customize")
    data object Flavors : AppScreens("flavors")
    data object Cart : AppScreens("cart")
    data object Profile : AppScreens("profile")
    data object Orders : AppScreens("orders")
    data object QrScanner : AppScreens("qr_scanner")
    data object Referral : AppScreens("referral")
    data object PaymentSuccess : AppScreens("payment_success")
    data object Tracking : AppScreens("tracking")

    data object AdminDashboard : AppScreens("admin_dashboard")
    data object AdminActiveProducts : AppScreens("admin_active_products")
    data object AdminCreateProduct : AppScreens("admin_create_product")

    data object CategoryMenu : AppScreens("category_menu/{category}") {
        fun createRoute(category: String) = "category_menu/$category"
    }
}