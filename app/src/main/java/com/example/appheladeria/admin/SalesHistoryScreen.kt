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
import com.example.appheladeria.ui.theme.*


data class SalesHistoryUiState(
    val selectedMonth: String = "Agosto 2023",
    val totalRevenue: String = "$0.00",
    val revenueTrend: String = "+0.0%",
    val totalOrders: String = "0",
    val ordersTrend: String = "+0.0%",
    val topFlavors: List<FlavorSales> = emptyList(),
    val recentTransactions: List<TransactionUi> = emptyList(),
    val highlightedDays: List<String> = emptyList()
)

data class FlavorSales(val name: String, val units: Int, val progress: Float)
data class TransactionUi(val id: String, val time: String, val items: List<String>, val price: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesHistoryScreen(
    state: SalesHistoryUiState = SalesHistoryUiState(),
    onBack: () -> Unit = {},
    onDateSelected: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onViewAllTransactions: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Ventas", fontWeight = FontWeight.Bold, color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextDark)
                    }
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick,
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
        bottomBar = { AdminBottomBar() },
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
                CalendarCard(
                    monthTitle = state.selectedMonth,
                    highlightedDays = state.highlightedDays,
                    onDayClick = onDateSelected
                ) 
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MiniStatCard("Ingresos Totales", state.totalRevenue, state.revenueTrend, Modifier.weight(1f))
                    MiniStatCard("Pedidos Totales", state.totalOrders, state.ordersTrend, Modifier.weight(1f))
                }
            }


            item { TopSellingFlavorsSection(flavors = state.topFlavors) }


            item { 
                RecentTransactionsSection(
                    transactions = state.recentTransactions,
                    onViewAll = onViewAllTransactions
                ) 
            }
        }
    }
}

@Composable
fun CalendarCard(
    monthTitle: String,
    highlightedDays: List<String>,
    onDayClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = PrimaryPink) }
                Text(text = monthTitle, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 16.sp)
                IconButton(onClick = {}) { Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryPink) }
            }

            val days = listOf("D", "L", "M", "M", "J", "V", "S")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                days.forEach { day ->
                    Text(text = day, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center, color = PrimaryPink, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val calendarDays = listOf(
                listOf("29", "30", "31", "1", "2", "3", "4"),
                listOf("5", "6", "7", "8", "9", "10", "11"),
                listOf("12", "13", "14", "15", "16", "17", "18"),
                listOf("19", "20", "21", "", "", "", "")
            )

            calendarDays.forEach { week ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    week.forEach { day ->
                        val isSelected = highlightedDays.contains(day)
                        val isGray = day == "29" || day == "30" || day == "31" || day.isEmpty()
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) PrimaryPink else Color.Transparent)
                                .clickable(enabled = day.isNotEmpty()) { onDayClick(day) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                color = if (isSelected) Color.White else if (isGray) Color.LightGray else TextDark,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
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

@Composable
fun TopSellingFlavorsSection(flavors: List<FlavorSales>) {
    Column {
        Text(text = "Sabores más vendidos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(16.dp))
        if (flavors.isEmpty()) {
            Text("No hay datos disponibles", color = TextMuted)
        } else {
            flavors.forEach { flavor ->
                FlavorProgressItem(flavor.name, flavor.units, flavor.progress)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun FlavorProgressItem(name: String, units: Int, progress: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = name, fontWeight = FontWeight.Medium, color = TextDark)
            Text(text = "$units unid.", fontWeight = FontWeight.Bold, color = PrimaryPink)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = PrimaryPink,
            trackColor = SecondaryPink.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun RecentTransactionsSection(transactions: List<TransactionUi>, onViewAll: () -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Transacciones Recientes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
            TextButton(onClick = onViewAll) { Text("Ver Todo", color = PrimaryPink) }
        }
        if (transactions.isEmpty()) {
            Text("No hay transacciones recientes", color = TextMuted, modifier = Modifier.padding(vertical = 10.dp))
        } else {
            transactions.forEach { transaction ->
                TransactionItem(transaction.id, transaction.time, transaction.items, transaction.price)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
    val mockState = SalesHistoryUiState(
        selectedMonth = "Agosto 2023",
        totalRevenue = "$4,250.80",
        revenueTrend = "+12.5%",
        totalOrders = "842",
        ordersTrend = "+5.2%",
        highlightedDays = listOf("5", "13"),
        topFlavors = listOf(
            FlavorSales("Fresa Bliss", 342, 1.0f),
            FlavorSales("Vainilla Velvet", 280, 0.8f),
            FlavorSales("Chocolate Intenso", 195, 0.6f)
        ),
        recentTransactions = listOf(
            TransactionUi("#8420", "Hoy, 2:45 PM", listOf("2x Fresa", "1x Choco"), "$24.50"),
            TransactionUi("#8419", "Hoy, 1:12 PM", listOf("1x Mango"), "$12.00")
        )
    )

    AppHeladeriaTheme {
        SalesHistoryScreen(state = mockState)
    }
}
