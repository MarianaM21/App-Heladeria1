package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SecondaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun ReferralScreen(
    onBack: () -> Unit
) {
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
            text = "Invita a tus amigos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Comparte la app y gana descuentos en tu próximo pedido 🎁",
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(SecondaryPink.copy(alpha = 0.18f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = PrimaryPink,
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Comparte App Heladeria",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Invita a tus amigos y obtén recompensas dulces 🍦",
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(22.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7FC))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Código de invitación",
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "SCOOP-2026",
                            color = PrimaryPink,
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Compartir")
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Copiar código",
                        color = TextDark
                    )
                }
            }
        }
    }
}