package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.SoftPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview
import com.example.appheladeria.ui.theme.AppHeladeriaTheme

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onFinish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🍨",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Scoop & Smile",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "llena de sabor tu día",
            color = TextMuted,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.size(24.dp))

        CircularProgressIndicator(color = PrimaryPink)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    AppHeladeriaTheme {
        SplashScreen(onFinish = {})
    }
}
