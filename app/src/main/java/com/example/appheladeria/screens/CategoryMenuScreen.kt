package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

data class CategoryItem(
    val name: String,
    val description: String,
    val price: Float,
    val category: String,
    val emoji: String
)

@Composable
fun CategoryMenuScreen(
    category: String,
    onBack: () -> Unit,
    onSelectItem: (CategoryItem) -> Unit
) {
    val allItems = listOf(
        CategoryItem("Cono Vainilla", "Clásico y cremoso", 5.5f, "Conos", "🍦"),
        CategoryItem("Cono Chocolate", "Intenso y delicioso", 6.0f, "Conos", "🍫"),
        CategoryItem("Fresa Dream", "Dulce y refrescante", 5.8f, "Frutales", "🍓"),
        CategoryItem("Berry Mix", "Frutas y crema", 6.1f, "Frutales", "🫐"),
        CategoryItem("Choco Brownie", "Chocolate con brownie", 6.3f, "Chocolates", "🍫"),
        CategoryItem("Oreo Choco", "Crujiente y cremoso", 6.4f, "Chocolates", "🍪"),
        CategoryItem("Alphonso Mango", "Tropical y especial", 6.5f, "Tropicales", "🥭"),
        CategoryItem("Piña Coco", "Fresco y tropical", 6.2f, "Tropicales", "🍍")
    )

    val filteredItems = allItems.filter { it.category == category }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .navigationBarsPadding()
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
            text = category,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Selecciona una opción para personalizarla",
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(16.dp))

        filteredItems.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { onSelectItem(item) },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.emoji,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.size(12.dp))

                        Column {
                            Text(
                                text = item.name,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDark
                            )
                            Text(
                                text = item.description,
                                color = TextMuted
                            )
                            Text(
                                text = "$${"%.2f".format(item.price.toDouble())}",
                                color = PrimaryPink,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Button(
                        onClick = { onSelectItem(item) },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                    ) {
                        Text("Elegir")
                    }
                }
            }
        }
    }
}