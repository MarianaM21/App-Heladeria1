package com.example.appheladeria.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun WelcomeScreen(
    onGoLogin: () -> Unit,
    onGoRegister: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFE3F1),
                        Color(0xFFFFF8FC),
                        Color(0xFFEFE6FF)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(22.dp)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(120.dp)
                .background(
                    color = PrimaryPink.copy(alpha = 0.12f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(170.dp)
                .background(
                    color = Color.White.copy(alpha = 0.45f),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🍦",
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Scoop & Smile",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Personaliza, ordena y disfruta tus helados favoritos en minutos.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 18.dp,
                        shape = RoundedCornerShape(36.dp)
                    ),
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.96f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MiniFeature(
                            emoji = "🍓",
                            title = "Sabores"
                        )

                        MiniFeature(
                            emoji = "🎁",
                            title = "Promos"
                        )

                        MiniFeature(
                            emoji = "📸",
                            title = "Spot"
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Elige tu experiencia",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Inicia sesión o crea tu cuenta para comenzar.",
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onGoLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryPink,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onGoRegister,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = PrimaryPink
                        )
                    ) {
                        Text(
                            text = "Crear cuenta",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryPink
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniFeature(
    emoji: String,
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .background(
                    color = Color(0xFFFFEEF7),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = TextDark,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    AppHeladeriaTheme {
        WelcomeScreen(
            onGoLogin = {},
            onGoRegister = {}
        )
    }
}