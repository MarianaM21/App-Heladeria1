package com.example.appheladeria.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
fun PaymentSuccessScreen(
    paidTotal: Float,
    onGoTracking: () -> Unit,
    onGoHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .navigationBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(PrimaryPink.copy(alpha = 0.14f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryPink,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¡Delicioso!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tu pedido ha sido realizado con éxito",
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7FC))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Pedido #4249",
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Total pagado: $${"%.2f".format(paidTotal.toDouble())}",
                            color = PrimaryPink,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = onGoTracking,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Icon(Icons.Default.DeliveryDining, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Seguir pedido")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onGoHome,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF3D7EA),
                        contentColor = TextDark
                    )
                ) {
                    Text("Volver al inicio")
                }
            }
        }
    }
}