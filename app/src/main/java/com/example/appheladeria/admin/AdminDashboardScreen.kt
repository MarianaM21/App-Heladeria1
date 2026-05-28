package com.example.appheladeria.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appheladeria.data.model.IceCreamItem
import com.example.appheladeria.ui.theme.*

@Composable
fun AdminDashboardScreen(
    products: List<IceCreamItem> = emptyList(),
    onGoCreateProduct: () -> Unit = {},
    onGoInventory: () -> Unit = {},
    onGoOrders: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = { AdminTopBar() },
        bottomBar = { 
            AdminBottomBar(
                currentRoute = "dashboard",
                onGoInventory = onGoInventory,
                onGoOrders = onGoOrders,
                onGoDashboard = { /* Already here */ }
            ) 
        },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard("Ventas hoy", "$1,240.50", "+12%", Modifier.weight(1f))
                    StatCard("Orden", "84", "+5%", Modifier.weight(1f))
                }
            }

            item {
                StatCard("Net Profit", "$890.20", "+8%", Modifier.fillMaxWidth())
            }

            item {
                SalesHistorySection(onClick = onGoOrders)
            }

            item {
                ActiveProductsSection(
                    products = products.take(3),
                    onGoCreateProduct = onGoCreateProduct,
                    onViewAll = onGoInventory
                )
            }

            item {
                AccountManagementSection(onLogout = onLogout)
            }
        }
    }
}

@Composable
fun AdminTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Dashboard Admin",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(40.dp)
                .background(SecondaryPink.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = PrimaryPink)
        }
    }
}

@Composable
fun StatCard(title: String, value: String, percentage: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryPink.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, color = TextMuted, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = TextDark)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = percentage, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SalesHistorySection(onClick: () -> Unit = {}) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Historial de ventas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
            TextButton(onClick = onClick) {
                Text(text = "Ver Pedidos", color = PrimaryPink)
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = onClick
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Ingresos Semanales", color = TextMuted, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "$8,650", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = TextDark)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "+15% la semana pasada", color = Color(0xFF4CAF50), fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                SimpleBarChart()
            }
        }
    }
}

@Composable
fun SimpleBarChart() {
    val days = listOf("Lun", "Mar", "Mier", "Jue", "Vier", "Sab", "Dom")
    val heights = listOf(0.4f, 0.9f, 0.6f, 0.3f, 0.75f, 0.85f, 0.2f)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        heights.forEachIndexed { index, height ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(35.dp)
                        .fillMaxHeight(height)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp))
                        .background(PrimaryPink.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(PrimaryPink)
                            .align(Alignment.TopCenter)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = days[index], fontSize = 10.sp, color = TextMuted)
            }
        }
    }
}

@Composable
fun ActiveProductsSection(
    products: List<IceCreamItem>,
    onGoCreateProduct: () -> Unit,
    onViewAll: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Productos Activos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
            Button(
                onClick = onGoCreateProduct,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Agregar Nuevo", fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (products.isEmpty()) {
                Text("No hay productos registrados", color = TextMuted, modifier = Modifier.padding(16.dp))
            } else {
                products.forEach { product ->
                    ProductListItem(product.name, "Inventario: ${product.inventory} unidades", "$${product.price}")
                }
                TextButton(onClick = onViewAll, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Ver todo el inventario", color = PrimaryPink)
                }
            }
        }
    }
}

@Composable
fun ProductListItem(name: String, inventory: String, price: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SecondaryPink.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Icecream, contentDescription = null, tint = PrimaryPink)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold, color = TextDark)
                Text(text = inventory, color = TextMuted, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = price, fontWeight = FontWeight.Bold, color = PrimaryPink)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun AccountManagementSection(onLogout: () -> Unit) {
    Column {
        Text(text = "Gestión de cuentas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                ManagementItem(Icons.Default.Person, "Información de perfil")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), thickness = 0.5.dp, color = BackgroundSoft)
                ManagementItem(Icons.Default.Security, "Configuración de seguridad")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), thickness = 0.5.dp, color = BackgroundSoft)
                ManagementItem(Icons.Default.Storefront, "Configuración de la tienda")
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), thickness = 0.5.dp, color = BackgroundSoft)
                ManagementItem(Icons.AutoMirrored.Filled.Logout, "Cerrar Sesión", isDestructive = true, onClick = onLogout)
            }
        }
    }
}

@Composable
fun ManagementItem(
    icon: ImageVector,
    title: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = if (isDestructive) PrimaryPink else PrimaryPink, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = title, fontWeight = FontWeight.Medium, color = if (isDestructive) PrimaryPink else TextDark)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun AdminBottomBar(
    currentRoute: String = "dashboard",
    onGoDashboard: () -> Unit = {},
    onGoInventory: () -> Unit = {},
    onGoOrders: () -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = onGoDashboard,
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, unselectedIconColor = TextMuted, unselectedTextColor = TextMuted, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = currentRoute == "inventario",
            onClick = onGoInventory,
            icon = { Icon(Icons.Default.Inventory, contentDescription = null) },
            label = { Text("Inventario") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, unselectedIconColor = TextMuted, unselectedTextColor = TextMuted, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = currentRoute == "pedidos",
            onClick = onGoOrders,
            icon = { Icon(Icons.Default.ReceiptLong, contentDescription = null) },
            label = { Text("Pedidos") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, unselectedIconColor = TextMuted, unselectedTextColor = TextMuted, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = currentRoute == "config",
            onClick = { },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Ajustes") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, unselectedIconColor = TextMuted, unselectedTextColor = TextMuted, indicatorColor = Color.Transparent)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminDashboardPreview() {
    AppHeladeriaTheme {
        AdminDashboardScreen()
    }
}
