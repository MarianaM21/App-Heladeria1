package com.example.appheladeria.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted
import kotlinx.coroutines.launch

data class PromotionUi(
    val name: String,
    val description: String,
    val tag: String,
    val price: Float,
    val oldPrice: Float,
    val emoji: String,
    val terms: String
)

@Composable
fun PromotionsScreen(
    onBack: () -> Unit,
    onAddPromotion: (PromotionUi) -> Unit,
    onGoCart: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val promotions = remember {
        listOf(
            PromotionUi(
                name = "Waffle Cones Promo",
                description = "Paga 1 y recibe 2 waffle cones para compartir.",
                tag = "2x1",
                price = 5.50f,
                oldPrice = 11.00f,
                emoji = "🧇",
                terms = "Válido únicamente al escanear el QR promocional."
            ),
            PromotionUi(
                name = "Combo Fresa Dream",
                description = "Helado de fresa con topping dulce y precio especial.",
                tag = "Combo",
                price = 7.50f,
                oldPrice = 9.50f,
                emoji = "🍓",
                terms = "Disponible hasta agotar existencias."
            ),
            PromotionUi(
                name = "Chocolate Lovers",
                description = "Chocolate cremoso con topping extra para amantes del cacao.",
                tag = "Especial",
                price = 8.00f,
                oldPrice = 10.00f,
                emoji = "🍫",
                terms = "Aplica para tamaño M."
            ),
            PromotionUi(
                name = "Familia Scoop",
                description = "Promoción familiar para disfrutar varios sabores.",
                tag = "Familiar",
                price = 18.00f,
                oldPrice = 24.00f,
                emoji = "🍨",
                terms = "Ideal para compartir."
            )
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextDark
                    )
                }
            }

            item {
                Text(
                    text = "Promociones secretas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Estas promociones se activaron con tu código QR 🍦",
                    color = TextMuted
                )
            }

            item {
                FeaturedQrCard()
            }

            itemsIndexed(promotions) { index, promotion ->

                PromotionCard(
                    promotion = promotion,
                    isFeatured = index == 0,
                    onAdd = {
                        onAddPromotion(promotion)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "${promotion.name} agregada al carrito"
                            )
                        }
                    }
                )
            }

            item {
                Button(
                    onClick = onGoCart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPink
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text("Ir al carrito")
                }
            }
        }
    }
}

@Composable
private fun FeaturedQrCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryPink)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "QR promocional detectado 🎉",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu código activó ofertas especiales que no aparecen en el menú principal.",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.18f)
                )
            ) {
                Text(
                    text = "Código: PROMO-QR",
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun PromotionCard(
    promotion: PromotionUi,
    isFeatured: Boolean,
    onAdd: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFeatured) SecondaryPink else Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = PrimaryPink.copy(alpha = 0.12f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = promotion.emoji,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Spacer(modifier = Modifier.size(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = promotion.tag,
                        color = PrimaryPink,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = promotion.name,
                        color = TextDark,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = promotion.description,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${"%.2f".format(promotion.price.toDouble())}",
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "$${"%.2f".format(promotion.oldPrice.toDouble())}",
                    color = TextMuted,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = promotion.terms,
                color = TextMuted,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFeatured) Color.White else PrimaryPink,
                    contentColor = if (isFeatured) PrimaryPink else Color.White
                )
            ) {
                Text("Agregar promoción")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PromotionsScreenPreview() {
    AppHeladeriaTheme {
        PromotionsScreen(
            onBack = {},
            onAddPromotion = {},
            onGoCart = {}
        )
    }
}