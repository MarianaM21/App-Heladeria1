package com.example.appheladeria.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.appheladeria.navigation.AppScreens

@Composable
fun AppBottomBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == AppScreens.Home.route,
            onClick = { navController.navigate(AppScreens.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == AppScreens.Orders.route,
            onClick = { navController.navigate(AppScreens.Orders.route) },
            icon = { Icon(Icons.Default.ReceiptLong, contentDescription = "Pedidos") },
            label = { Text("Pedidos") }
        )

        NavigationBarItem(
            selected = currentRoute == AppScreens.QrScanner.route,
            onClick = { navController.navigate(AppScreens.QrScanner.route) },
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "QR") },
            label = { Text("QR") }
        )

        NavigationBarItem(
            selected = currentRoute == AppScreens.Profile.route,
            onClick = { navController.navigate(AppScreens.Profile.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}