package com.example.appheladeria.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appheladeria.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductScreen(
    onBack: () -> Unit = {},
    onProductCreated: (AdminProduct) -> Unit = {}
) {
    var name by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Product", fontWeight = FontWeight.Bold, color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(SecondaryPink.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(32.dp))
                    Text("Add Image", fontSize = 12.sp, color = PrimaryPink, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            AdminTextField(
                value = name,
                onValueChange = { name = it },
                label = "Product Name",
                placeholder = "e.g. Vanilla Bean Classic"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = "Stock",
                    placeholder = "0",
                    modifier = Modifier.weight(1f)
                )
                AdminTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = "Price ($)",
                    placeholder = "0.00",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AdminTextField(
                value = description,
                onValueChange = { description = it },
                label = "Description",
                placeholder = "Describe the product...",
                singleLine = false,
                modifier = Modifier.height(120.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    val stockInt = stock.toIntOrNull() ?: 0
                    val priceFloat = price.toFloatOrNull() ?: 0f
                    if (name.isNotBlank()) {
                        onProductCreated(AdminProduct(name, stockInt, priceFloat))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
            ) {
                Text("Create Product", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AdminTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    Column(modifier = modifier) {
        Text(text = label, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(placeholder, color = TextMuted) },
            singleLine = singleLine,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = PrimaryPink,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = PrimaryPink
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateProductPreview() {
    AppHeladeriaTheme {
        CreateProductScreen()
    }
}
