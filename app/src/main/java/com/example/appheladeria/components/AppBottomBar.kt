package com.example.appheladeria.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appheladeria.navigation.AppScreens
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun AppBottomBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val items = listOf(
        NavigationItem("Inicio", AppScreens.Home.route, Icons.Default.Home),
        NavigationItem("Pedidos", AppScreens.Orders.route, Icons.Default.ReceiptLong),
        NavigationItem("QR", AppScreens.QrScanner.route, Icons.Default.QrCodeScanner),
        NavigationItem("Perfil", AppScreens.Profile.route, Icons.Default.Person)
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = PrimaryPink
    ) {
        items.forEach { item ->
            val isSelected = when (item.route) {
                AppScreens.Home.route -> currentRoute in listOf(
                    AppScreens.Home.route,
                    AppScreens.Customize.route,
                    AppScreens.Flavors.route,
                    AppScreens.Cart.route
                )
                else -> currentRoute == item.route
            }
            
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = item.icon, 
                        contentDescription = item.title,
                        tint = if (isSelected) PrimaryPink else TextMuted
                    ) 
                },
                label = { 
                    Text(
                        text = item.title,
                        color = if (isSelected) PrimaryPink else TextMuted
                    ) 
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = PrimaryPink.copy(alpha = 0.1f)
                )
            )
        }
    }
}

private data class NavigationItem(
    val title: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    val navController = rememberNavController()
    AppHeladeriaTheme {
        AppBottomBar(
            navController = navController,
            currentRoute = AppScreens.Home.route
        )
    }
}
