package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

data class IceCream(
    val name: String,
    val price: Float,
    val emoji: String,
    val description: String
)

@Composable
fun HomeScreen(
    userName: String,
    cartCount: Int,
    cartTotal: Float,
    onAddPromo: () -> Unit,
    onLogout: () -> Unit,
    onGoCustomize: () -> Unit,
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onGoOrders: () -> Unit,
    onGoQr: () -> Unit,
    onGoReferral: () -> Unit,
    onAddTrending: (IceCream) -> Unit
) {
    var search by rememberSaveable { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }

    val categories = listOf("🍦 Conos", "🍓 Frutales", "🍫 Chocolates", "🥭 Tropicales")

    val trending = remember {
        listOf(
            IceCream("Vainilla", 5.50f, "🍦", "Suave y clásica"),
            IceCream("Chocolate", 6.00f, "🍫", "Intenso y cremoso"),
            IceCream("Fresa Dream", 5.80f, "🍓", "Dulce y refrescante"),
            IceCream("Alphonso Mango", 6.50f, "🥭", "Tropical y delicioso"),
            IceCream("Oreo Crush", 6.20f, "🍪", "Crujiente y cremoso"),
            IceCream("Pistacho", 6.20f, "🥜", "Elegante y suave")
        )
    }

    val filteredTrending = remember(search, trending) {
        val query = search.trim()
        if (query.isBlank()) {
            trending
        } else {
            trending.filter { item ->
                item.name.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Hola, ${if (userName.isBlank()) "Mariana Martinez" else userName}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Text(
                    text = "👋",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "¿Qué vas a pedir hoy?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
            }

            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = TextDark
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = search,
                onValueChange = { 
                    search = it
                    showSuggestions = it.isNotBlank()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                label = { Text("Buscar helado, topping o combo") },
                placeholder = { Text("Ej: vainilla, chocolate, mango") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryPink)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPink,
                    unfocusedBorderColor = Color(0xFFB9B0BA),
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark,
                    focusedLabelColor = PrimaryPink,
                    unfocusedLabelColor = TextDark,
                    focusedPlaceholderColor = TextMuted,
                    unfocusedPlaceholderColor = TextMuted,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = PrimaryPink
                )
            )

            DropdownMenu(
                expanded = showSuggestions && filteredTrending.isNotEmpty(),
                onDismissRequest = { showSuggestions = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                filteredTrending.take(5).forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = item.emoji, modifier = Modifier.padding(end = 8.dp))
                                Column {
                                    Text(text = item.name, fontWeight = FontWeight.Bold, color = TextDark)
                                    Text(text = "$${"%.2f".format(item.price)}", style = MaterialTheme.typography.bodySmall, color = PrimaryPink)
                                }
                            }
                        },
                        onClick = {
                            search = item.name
                            showSuggestions = false
                            onAddTrending(item)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = SecondaryPink)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Promoción especial 🎉",
                    color = TextDark,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "2x1 en Waffle Cones",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Aprovecha hoy y agrégalo al carrito",
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAddPromo,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = PrimaryPink
                    )
                ) {
                    Text("Agregar promo")
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SmallNavCard("Personalizar", Icons.Default.ShoppingCart, onGoCustomize, Modifier.weight(1f))
            SmallNavCard("Perfil", Icons.Default.Person, onGoProfile, Modifier.weight(1f))
            SmallNavCard("Pedidos", Icons.Default.ReceiptLong, onGoOrders, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SmallNavCard("QR", Icons.Default.QrCodeScanner, onGoQr, Modifier.weight(1f))
            SmallNavCard("Invitar", Icons.Default.Share, onGoReferral, Modifier.weight(1f))
            SmallNavCard("Carrito", Icons.Default.ShoppingCart, onGoCart, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Categorías",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(categories) { category ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.clickable { onGoCustomize() }
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            color = TextDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (search.isBlank()) "🔥 En tendencia" else "Resultados de búsqueda",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            if (filteredTrending.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "No encontramos resultados para \"$search\"",
                            modifier = Modifier.padding(16.dp),
                            color = TextDark
                        )
                    }
                }
            } else {
                items(filteredTrending) { item ->
                    IceCreamCard(
                        item = item,
                        onAdd = onAddTrending
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGoCart() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = PrimaryPink
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            "Carrito",
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark
                        )
                        Text(
                            "$cartCount producto(s)",
                            color = TextMuted
                        )
                    }
                }

                Text(
                    text = "$${"%.2f".format(cartTotal.toDouble())}",
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun IceCreamCard(
    item: IceCream,
    onAdd: (IceCream) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.emoji,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "⭐ ${item.description}",
                    color = TextMuted
                )
                Text(
                    text = "$${"%.2f".format(item.price.toDouble())}",
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { onAdd(item) },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
            ) {
                Text("Añadir")
            }
        }
    }
}

@Composable
private fun SmallNavCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryPink
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppHeladeriaTheme {
        HomeScreen(
            userName = "Mariana",
            cartCount = 2,
            cartTotal = 12.50f,
            onAddPromo = {},
            onLogout = {},
            onGoCustomize = {},
            onGoCart = {},
            onGoProfile = {},
            onGoOrders = {},
            onGoQr = {},
            onGoReferral = {},
            onAddTrending = {}
        )
    }
}
