package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFD7EC),
                        Color(0xFFFFF4FA),
                        Color(0xFFEADFFF)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        DecorativeBubble(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(150.dp),
            color = Color.White.copy(alpha = 0.35f)
        )

        DecorativeBubble(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(190.dp),
            color = PrimaryPink.copy(alpha = 0.16f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier
                    .size(150.dp)
                    .shadow(
                        elevation = 18.dp,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍨",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Scoop & Smile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(30.dp),
                color = Color.White.copy(alpha = 0.70f)
            ) {
                Text(
                    text = "Helados que alegran tu día",
                    modifier = Modifier
                        .background(Color.Transparent)
                        .height(40.dp),
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(
                color = PrimaryPink,
                strokeWidth = 4.dp,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

@Composable
private fun DecorativeBubble(
    modifier: Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    AppHeladeriaTheme {
        SplashScreen(onFinish = {})
    }
}