package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.BorderSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

data class ToppingUi(
    val name: String,
    val emoji: String
)

@Composable
fun CustomizeScreen(
    initialFlavor: String,
    initialTopping: String,
    initialSize: String,
    onBack: () -> Unit,
    onGoFlavors: () -> Unit,
    onAddToCart: (String, String, String, Float) -> Unit
) {
    val sizes = listOf("S", "M", "L")

    val toppings = listOf(
        ToppingUi("Rainbow Sprinkles", "🍬"),
        ToppingUi("Choco Chips", "🍫"),
        ToppingUi("Hot Fudge", "🍩")
    )

    var selectedFlavor by rememberSaveable {
        mutableStateOf(initialFlavor.ifBlank { "Vainilla" })
    }
    var selectedTopping by rememberSaveable {
        mutableStateOf(initialTopping.ifBlank { "Rainbow Sprinkles" })
    }
    var selectedSize by rememberSaveable {
        mutableStateOf(initialSize.ifBlank { "M" })
    }

    LaunchedEffect(initialFlavor) {
        if (initialFlavor.isNotBlank()) {
            selectedFlavor = initialFlavor
        }
    }

    LaunchedEffect(initialTopping) {
        if (initialTopping.isNotBlank()) {
            selectedTopping = initialTopping
        }
    }

    LaunchedEffect(initialSize) {
        if (initialSize.isNotBlank()) {
            selectedSize = initialSize
        }
    }

    val basePrice = when (selectedFlavor) {
        "Chocolate" -> 6.00f
        "Fresa Dream" -> 5.80f
        "Alphonso Mango" -> 6.50f
        "Oreo Crush" -> 6.20f
        "Pistacho" -> 6.20f
        "Moonlight Cookies" -> 6.40f
        else -> 5.50f
    }

    val sizeExtra = when (selectedSize) {
        "S" -> 0f
        "M" -> 1.00f
        "L" -> 2.00f
        else -> 1.00f
    }

    val toppingExtra = when (selectedTopping) {
        "Rainbow Sprinkles" -> 0.50f
        "Choco Chips" -> 0.80f
        "Hot Fudge" -> 1.00f
        else -> 0f
    }

    val totalPrice = basePrice + sizeExtra + toppingExtra

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
            text = "Personalizar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Elegir tamaño",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    sizes.forEach { size ->
                        SizeOption(
                            text = size,
                            selected = selectedSize == size,
                            onClick = { selectedSize = size }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Elegir sabor",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlavorPreviewCard(
                    flavor = selectedFlavor,
                    onClick = onGoFlavors
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Topping extra",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    toppings.forEach { topping ->
                        ToppingCard(
                            topping = topping,
                            selected = selectedTopping == topping.name,
                            onClick = { selectedTopping = topping.name }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Precio: $${"%.2f".format(totalPrice.toDouble())}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryPink
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onAddToCart(
                            selectedFlavor,
                            selectedTopping,
                            selectedSize,
                            totalPrice
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}

@Composable
private fun SizeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val border = if (selected) PrimaryPink else BorderSoft
    val bg = if (selected) PrimaryPink.copy(alpha = 0.10f) else Color.White

    Box(
        modifier = Modifier
            .size(58.dp)
            .background(bg, CircleShape)
            .border(2.dp, border, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            color = if (selected) PrimaryPink else TextDark
        )
    }
}

@Composable
private fun FlavorPreviewCard(
    flavor: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8FC))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(PrimaryPink.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍨",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = flavor,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Text(
                    text = "Toca para cambiar sabor",
                    color = TextMuted
                )
            }

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
            ) {
                Text("Elegir")
            }
        }
    }
}

@Composable
private fun ToppingCard(
    topping: ToppingUi,
    selected: Boolean,
    onClick: () -> Unit
) {
    val border = if (selected) PrimaryPink else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, border, RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8FC))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(PrimaryPink.copy(alpha = 0.10f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = topping.emoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = topping.name,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = if (selected) "Seleccionado" else "Toca para elegir",
                    color = if (selected) PrimaryPink else TextMuted
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomizeScreenPreview() {
    CustomizeScreen(
        initialFlavor = "Chocolate",
        initialTopping = "Choco Chips",
        initialSize = "L",
        onBack = {},
        onGoFlavors = {},
        onAddToCart = { _, _, _, _ -> }
    )
}
