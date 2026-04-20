package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.CardSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

data class OrderUi(
    val id: String,
    val status: String,
    val total: String,
    val summary: String
)

@Composable
fun OrdersScreen() {
    val orders = listOf(
        OrderUi(
            id = "#4249",
            status = "Entregado",
            total = "$34.50",
            summary = "2 helados + 1 topping"
        ),
        OrderUi(
            id = "#7832",
            status = "En camino",
            total = "$18.00",
            summary = "1 waffle cone + extra fudge"
        ),
        OrderUi(
            id = "#7998",
            status = "En preparación",
            total = "$25.50",
            summary = "3 helados personalizados"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Revisa el estado de tus pedidos 🍨",
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(orders) { order ->
                OrderCard(order = order)
            }
        }
    }
}

@Composable
private fun OrderCard(order: OrderUi) {
    val statusColor = when (order.status) {
        "Entregado" -> Color(0xFF2E9B57)
        "En camino" -> PrimaryPink
        else -> Color(0xFFE28A00)
    }

    val statusIcon = when (order.status) {
        "Entregado" -> Icons.Default.Done
        "En camino" -> Icons.Default.DeliveryDining
        else -> Icons.Default.Icecream
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pedido ${order.id}",
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = order.summary,
                        color = TextMuted
                    )
                }

                Text(
                    text = order.total,
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSoft)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = statusColor
                    )

                    Spacer(modifier = Modifier.height(0.dp).padding(start = 8.dp))

                    Text(
                        text = order.status,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}