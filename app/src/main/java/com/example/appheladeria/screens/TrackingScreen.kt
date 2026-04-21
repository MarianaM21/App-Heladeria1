package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun TrackingScreen(
    onBack: () -> Unit,
    onGoHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextDark
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sigue tu helado",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Tu pedido ya va en camino 🍦",
                color = TextMuted
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEAF4FF)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PrimaryPink,
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Mapa de seguimiento",
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "Entrega estimada en 10 - 15 min",
                        color = TextMuted
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Pedido #4249",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = PrimaryPink)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Llega en 10 - 15 min",
                        color = PrimaryPink,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                LinearProgressIndicator(
                    progress = { 0.72f },
                    modifier = Modifier.fillMaxWidth(),
                    color = PrimaryPink,
                    trackColor = SecondaryPink.copy(alpha = 0.18f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                TrackingStep(
                    icon = Icons.Default.Storefront,
                    title = "Pedido confirmado",
                    subtitle = "Tu orden fue recibida"
                )

                TrackingStep(
                    icon = Icons.Default.DeliveryDining,
                    title = "En camino",
                    subtitle = "El repartidor ya salió"
                )

                TrackingStep(
                    icon = Icons.Default.LocationOn,
                    title = "Próximo destino",
                    subtitle = "Tu ubicación actual"
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onGoHome,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Text("Volver al inicio")
                }
            }
        }
    }
}

@Composable
private fun TrackingStep(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(PrimaryPink.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryPink)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(
                text = subtitle,
                color = TextMuted
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrackingScreenPreview() {
    AppHeladeriaTheme {
        TrackingScreen(
            onBack = {},
            onGoHome = {}
        )
    }
}
