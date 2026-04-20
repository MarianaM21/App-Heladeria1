package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import com.example.appheladeria.data.model.CartProduct
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.LavenderCard
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun CartScreen(
    cartItems: List<CartProduct>,
    cartCount: Int,
    cartTotal: Float,
    onBack: () -> Unit,
    onPayNow: () -> Unit,
    onRemoveItem: (Int) -> Unit
) {
    var saveCard by remember { mutableStateOf(true) }
    var address by rememberSaveable { mutableStateOf("Mi ubicación actual") }

    val shipping = 5.00f
    val finalTotal = cartTotal + shipping

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = TextDark
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Finalizar compra",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ===== RESUMEN PEDIDO =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Resumen Pedido",
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (cartItems.isEmpty()) {
                    Text(
                        text = "Tu carrito está vacío",
                        color = TextMuted
                    )
                } else {
                    cartItems.forEachIndexed { index, product ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8FC))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${product.flavor} (${product.size})",
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "Topping: ${product.topping}",
                                        color = TextMuted
                                    )

                                    Text(
                                        text = "Cantidad: ${product.quantity}",
                                        color = TextMuted
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = "$${"%.2f".format((product.price * product.quantity).toDouble())}",
                                        color = PrimaryPink,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                                IconButton(onClick = { onRemoveItem(index) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = PrimaryPink
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Items: $cartCount",
                    color = TextMuted
                )

                Text(
                    text = "Subtotal: $${"%.2f".format(cartTotal.toDouble())}",
                    color = TextMuted
                )

                Text(
                    text = "Envío: $${"%.2f".format(shipping.toDouble())}",
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: $${"%.2f".format(finalTotal.toDouble())}",
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== DIRECCIÓN =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = LavenderCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextMuted
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Dirección de entrega",
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.EditLocationAlt,
                            contentDescription = null,
                            tint = PrimaryPink
                        )
                    },
                    placeholder = {
                        Text("Ingresa tu dirección", color = TextMuted)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPink,
                        unfocusedBorderColor = Color(0xFFB9B0BA),
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark,
                        focusedPlaceholderColor = TextMuted,
                        unfocusedPlaceholderColor = TextMuted,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = PrimaryPink
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== PAGO =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = LavenderCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Payment,
                        contentDescription = null,
                        tint = TextMuted
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Método de pago",
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tarjeta **** 1212",
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Guardar tarjeta", color = TextMuted)

                    Switch(
                        checked = saveCard,
                        onCheckedChange = { saveCard = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = PrimaryPink,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFFD8D2DA)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onPayNow,
            enabled = cartItems.isNotEmpty() && address.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryPink,
                disabledContainerColor = PrimaryPink.copy(alpha = 0.45f),
                disabledContentColor = Color.White
            )
        ) {
            Text("Pagar ahora")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}