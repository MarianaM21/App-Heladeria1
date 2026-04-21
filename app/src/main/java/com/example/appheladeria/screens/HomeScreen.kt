package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted
import kotlinx.coroutines.launch

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
    onGoCart: () -> Unit,
    onGoProfile: () -> Unit,
    onGoOrders: () -> Unit,
    onGoQr: () -> Unit,
    onGoReferral: () -> Unit,
    onGoCategory: (String) -> Unit,
    onAddTrending: (IceCream) -> Unit
) {
    var search by rememberSaveable { mutableStateOf("") }
    var showSuggestions by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val categories = listOf(
        "Conos",
        "Frutales",
        "Chocolates",
        "Tropicales"
    )

    val trending = remember {
        listOf(
            IceCream("Pistacho Dream", 4.50f, "🍵", "Suave y elegante"),
            IceCream("Caramel Bliss", 4.80f, "🍮", "Dulce y cremoso"),
            IceCream("Vainilla", 5.50f, "🍦", "Clásica y suave"),
            IceCream("Chocolate", 6.00f, "🍫", "Intenso y cremoso"),
            IceCream("Fresa Dream", 5.80f, "🍓", "Dulce y refrescante"),
            IceCream("Alphonso Mango", 6.50f, "🥭", "Tropical y delicioso")
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

    val searchSuggestions = remember(search, trending) {
        val query = search.trim()
        if (query.isBlank()) {
            emptyList()
        } else {
            trending.filter {
                it.name.contains(query, ignoreCase = true)
            }.take(4)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundSoft)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Hola, ${if (userName.isBlank()) "mariana" else userName}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDark,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(2.dp))

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

                    Spacer(modifier = Modifier.height(10.dp))

                    Box {
                        OutlinedTextField(
                            value = search,
                            onValueChange = {
                                search = it
                                showSuggestions = it.isNotBlank()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            singleLine = true,
                            label = { Text("Buscar helado, topping o combo") },
                            placeholder = { Text("Ej: vainilla, chocolate, mango") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = PrimaryPink
                                )
                            },
                            trailingIcon = {
                                if (search.isNotBlank()) {
                                    IconButton(
                                        onClick = {
                                            search = ""
                                            showSuggestions = false
                                        }
                                    ) {
                                        Text(
                                            text = "✕",
                                            color = PrimaryPink,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
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
                            expanded = showSuggestions && searchSuggestions.isNotEmpty(),
                            onDismissRequest = {
                                showSuggestions = false
                            },
                            modifier = Modifier.fillMaxWidth(0.92f)
                        ) {
                            searchSuggestions.forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = item.name,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = item.description,
                                                color = TextMuted,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    },
                                    onClick = {
                                        onAddTrending(item)
                                        search = ""
                                        showSuggestions = false
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "${item.name} añadido al carrito"
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                CompactPromoCard(
                    onAddPromo = {
                        onAddPromo()
                        scope.launch {
                            snackbarHostState.showSnackbar("Promo añadida al carrito")
                        }
                    }
                )
            }

            item {
                QuickActionsRow(
                    onGoProfile = onGoProfile,
                    onGoOrders = onGoOrders,
                    onGoQr = onGoQr,
                    onGoReferral = onGoReferral,
                    onGoCart = onGoCart
                )
            }

            item {
                SectionTitle("Categorías")
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            title = category,
                            onClick = { onGoCategory(category) }
                        )
                    }
                }
            }

            item {
                SectionTitle("EN TENDENCIA AHORA")
            }

            item {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredTrending.take(6)) { item ->
                        TrendingCard(
                            item = item,
                            onAdd = {
                                onAddTrending(item)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "${item.name} añadido al carrito"
                                    )
                                }
                            }
                        )
                    }
                }
            }

            item {
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
                                    text = "Carrito",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextDark
                                )
                                Text(
                                    text = "$cartCount producto(s)",
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
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
    )
}

@Composable
private fun CompactPromoCard(
    onAddPromo: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryPink)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Promoción especial 🎉",
                color = TextDark,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

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

            Spacer(modifier = Modifier.height(14.dp))

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
}

@Composable
private fun QuickActionsRow(
    onGoProfile: () -> Unit,
    onGoOrders: () -> Unit,
    onGoQr: () -> Unit,
    onGoReferral: () -> Unit,
    onGoCart: () -> Unit
) {
    val actions = listOf(
        Triple("Pedidos", Icons.Default.ReceiptLong, onGoOrders),
        Triple("Invitar", Icons.Default.Share, onGoReferral),
        Triple("Perfil", Icons.Default.Person, onGoProfile),
        Triple("QR", Icons.Default.QrCodeScanner, onGoQr),
        Triple("Carrito", Icons.Default.ShoppingCart, onGoCart)
    )

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(actions) { action ->
            SmallNavCardCompact(
                title = action.first,
                icon = action.second,
                onClick = action.third
            )
        }
    }
}

@Composable
private fun SmallNavCardCompact(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(112.dp)
            .height(76.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryPink)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun CategoryChip(
    title: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (title) {
                    "Conos" -> "🍦 Conos"
                    "Frutales" -> "🍓 Frutales"
                    "Chocolates" -> "🍫 Chocolates"
                    "Tropicales" -> "🥭 Tropicales"
                    else -> title
                },
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun TrendingCard(
    item: IceCream,
    onAdd: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .aspectRatio(0.78f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = SecondaryPink.copy(alpha = 0.22f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.emoji,
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.name,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.description,
                color = TextMuted,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${"%.2f".format(item.price.toDouble())}",
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(PrimaryPink, CircleShape)
                        .clickable { onAdd() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}