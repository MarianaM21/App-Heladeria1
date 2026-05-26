package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.PrimaryPink

@Composable
fun PhotoSpotScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Spot Heladería 🍦",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Tómate una selfie y compártela",
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    5.dp,
                    PrimaryPink,
                    RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "📸 Cámara Frontal",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Foto")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compartir")
        }
    }
}