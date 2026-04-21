package com.example.appheladeria.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appheladeria.R
import com.example.appheladeria.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductConfirmationScreen(
    onBack: () -> Unit = {},
    onGoDashboard: () -> Unit = {},
    onAddAnother: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Confirmación", 
                        modifier = Modifier.fillMaxWidth().padding(end = 48.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    ) 
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .background(SecondaryPink.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Back", 
                            tint = PrimaryPink,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = { AdminBottomBar() },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Central Illustration
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, SecondaryPink.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Icecream,
                        contentDescription = null,
                        tint = PrimaryPink,
                        modifier = Modifier.size(80.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(PrimaryPink, CircleShape)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "¡Producto Guardado!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "El nuevo sabor ha sido agregado exitosamente a tu catálogo.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Buttons
            Button(
                onClick = onGoDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
            ) {
                Icon(Icons.Default.Dashboard, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Volver al Dashboard", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onAddAnother,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                border = BoxShadow(1.dp, PrimaryPink), // Simple simulation of the border
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryPink)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Añadir otro producto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Image Decorative
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(SecondaryPink.copy(alpha = 0.1f))) {
                    // In a real app, you would use an Image here
                    Icon(
                        Icons.Default.Icecream, 
                        contentDescription = null, 
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        tint = PrimaryPink.copy(alpha = 0.1f)
                    )
                }
            }
        }
    }
}

private fun BoxShadow(width: androidx.compose.ui.unit.Dp, color: Color) = 
    androidx.compose.foundation.BorderStroke(width, color)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductConfirmationPreview() {
    AppHeladeriaTheme {
        ProductConfirmationScreen()
    }
}
