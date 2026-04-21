package com.example.appheladeria.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appheladeria.ui.theme.*

data class AdminProduct(
    val name: String,
    val stock: Int,
    val price: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveProductsScreen(
    onBack: () -> Unit = {},
    onAddNewProduct: () -> Unit = {},
    onEditProduct: (AdminProduct) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val products = remember {
        mutableStateListOf(
            AdminProduct("Vainilla", 120, 4.50f),
            AdminProduct("Chocolate", 85, 5.00f),
            AdminProduct("Fresa", 40, 4.75f),
            AdminProduct("Menta de chocolate", 62, 4.95f),
            AdminProduct("Caramelo Salteado", 12, 5.25f)
        )
    }

    val filteredProducts = products.filter { 
        it.name.contains(searchQuery, ignoreCase = true) 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos Activos", fontWeight = FontWeight.Bold, color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = TextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick = onAddNewProduct,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agrega un nuevo producto", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = BackgroundSoft
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(28.dp),
                placeholder = { Text("Buscar productos activos...", color = TextMuted) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryPink) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = SecondaryPink.copy(alpha = 0.1f),
                    unfocusedContainerColor = SecondaryPink.copy(alpha = 0.1f),
                    cursorColor = PrimaryPink
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                itemsIndexed(filteredProducts) { _, product ->
                    ActiveProductItem(
                        product = product,
                        onEdit = { onEditProduct(product) },
                        onDelete = { products.remove(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun ActiveProductItem(
    product: AdminProduct,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Icecream, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(35.dp))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = TextDark
            )
            val stockText = if (product.stock < 15) "Bajo Stock: ${product.stock} unidades" else "Unidades en Stock: ${product.stock} "
            Text(
                text = stockText,
                color = if (product.stock < 15) Color.Red else PrimaryPink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${"%.2f".format(product.price)}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .size(32.dp)
                        .background(SecondaryPink.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryPink, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(32.dp)
                        .background(SecondaryPink.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = PrimaryPink, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActiveProductsPreview() {
    AppHeladeriaTheme {
        ActiveProductsScreen()
    }
}
