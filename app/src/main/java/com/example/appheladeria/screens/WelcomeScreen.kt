package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun WelcomeScreen(
    onGoLogin: () -> Unit,
    onGoRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .navigationBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🍦",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Scoop & Smile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Helados deliciosos para alegrar tu día",
                    color = TextMuted,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onGoLogin,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Text("Iniciar sesión")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onGoRegister,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF5D8EA),
                        contentColor = TextDark
                    )
                ) {
                    Text("Crear cuenta")
                }
            }
        }
    }
}