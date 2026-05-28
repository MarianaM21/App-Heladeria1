package com.example.appheladeria.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appheladeria.data.model.Order
import com.example.appheladeria.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesHistoryScreen(
    orders: List<Order> = emptyList(),
    onBack: () -> Unit = {},
    onGoInventory: () -> Unit = {},
    onGoDashboard: () -> Unit = {}
) {
    val totalRevenue = orders.sumOf { it.total }
    val totalOrders = orders.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedidos de Usuarios", fontWeight = FontWeight.Bold, color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextDark)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .background(SecondaryPink.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = PrimaryPink)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = { 
            AdminBottomBar(
                currentRoute = "pedidos",
                onGoInventory = onGoInventory,
                onGoDashboard = onGoDashboard
            ) 
        },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MiniStatCard("Ingresos Totales", "$${"%.2f".format(totalRevenue)}", "+12.5%", Modifier.weight(1f))
                    MiniStatCard("Pedidos Totales", totalOrders.toString(), "+5.2%", Modifier.weight(1f))
                }
            }

            item {
                Text(
                    text = "Transacciones Recientes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            if (orders.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Text("No hay pedidos registrados aún", color = TextMuted)
                    }
                }
            } else {
                items(orders) { order ->
                    TransactionItem(
                        id = "#${order.id}",
                        time = formatTimestamp(order.timestamp),
                        items = order.items.map { "${it.quantity}x ${it.flavor}" },
                        price = "$${"%.2f".format(order.total)}"
                    )
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun MiniStatCard(title: String, value: String, trend: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryPink.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, color = TextMuted, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextDark)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(12.dp))
                Text(text = trend, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransactionItem(id: String, time: String, items: List<String>, price: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = "Orden $id", fontWeight = FontWeight.Bold, color = TextDark)
                    Text(text = time, color = TextMuted, fontSize = 12.sp)
                }
                Text(text = price, fontWeight = FontWeight.ExtraBold, color = PrimaryPink, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .background(SecondaryPink.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = item, color = PrimaryPink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SalesHistoryPreview() {
    AppHeladeriaTheme {
        SalesHistoryScreen()
    }
}
