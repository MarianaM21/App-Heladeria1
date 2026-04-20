package com.example.appheladeria.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.R
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

data class FlavorUi(
    val name: String,
    val price: Float,
    val imageRes: Int,
    val description: String
)

@Composable
fun FlavorsScreen(
    onBack: () -> Unit,
    onSelectFlavor: (String) -> Unit
) {
    val flavors = listOf(
        FlavorUi("Vainilla", 5.50f, R.drawable.img_vainilla, "Clásica, suave y cremosa"),
        FlavorUi("Chocolate", 6.00f, R.drawable.img_chocolate, "Intenso y delicioso"),
        FlavorUi("Fresa Dream", 5.80f, R.drawable.img_strawberry, "Dulce y refrescante"),
        FlavorUi("Pistacho", 6.20f, R.drawable.img_pistacho, "Elegante y suave"),
        FlavorUi("Moonlight Cookies", 6.40f, R.drawable.img_cookies, "Crujiente y cremoso"),
        FlavorUi("Alphonso Mango", 6.50f, R.drawable.img_mango, "Tropical y especial")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
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
            text = "Elegir sabor",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Escoge tu sabor favorito para continuar",
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(flavors) { flavor ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .background(
                                    color = SecondaryPink.copy(alpha = 0.18f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = flavor.imageRes),
                                contentDescription = flavor.name,
                                modifier = Modifier.size(42.dp)
                            )
                        }

                        Spacer(modifier = Modifier.size(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = flavor.name,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDark
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = flavor.description,
                                color = TextMuted
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "$${"%.2f".format(flavor.price.toDouble())}",
                                color = PrimaryPink,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = { onSelectFlavor(flavor.name) },
                            shape = RoundedCornerShape(22.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                        ) {
                            Text("Elegir")
                        }
                    }
                }
            }
        }
    }
}