package com.example.appheladeria.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    onGoLogin: () -> Unit = {}
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var formError by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = TextDark
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Únete a la diversión",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Crea tu cuenta para comenzar a pedir",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(22.dp))

                RegisterField(
                    value = name,
                    onValueChange = {
                        name = it
                        formError = ""
                    },
                    label = "Nombre completo"
                )

                Spacer(modifier = Modifier.height(14.dp))

                RegisterField(
                    value = email,
                    onValueChange = {
                        email = it
                        formError = ""
                    },
                    label = "Correo electrónico"
                )

                Spacer(modifier = Modifier.height(14.dp))

                RegisterField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        formError = ""
                    },
                    label = "Teléfono"
                )

                Spacer(modifier = Modifier.height(14.dp))

                RegisterField(
                    value = password,
                    onValueChange = {
                        password = it
                        formError = ""
                    },
                    label = "Contraseña",
                    isPassword = true
                )

                if (formError.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = formError,
                        color = PrimaryPink,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        when {
                            name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() -> {
                                formError = "Todos los campos son obligatorios"
                            }

                            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                                formError = "Correo electrónico no válido"
                            }

                            phone.trim().length < 7 -> {
                                formError = "Teléfono no válido"
                            }

                            password.trim().length < 6 -> {
                                formError = "La contraseña debe tener al menos 6 caracteres"
                            }

                            else -> {
                                formError = ""
                                onRegister(
                                    name.trim(),
                                    email.trim(),
                                    password.trim(),
                                    phone.trim()
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Text("Crear cuenta")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onGoLogin) {
                    Text(
                        text = "Ya tengo una cuenta",
                        color = PrimaryPink
                    )
                }
            }
        }
    }
}

@Composable
private fun RegisterField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(22.dp),
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        label = {
            Text(
                text = label,
                color = TextDark
            )
        },
        placeholder = {
            Text(
                text = "Obligatorio",
                color = TextMuted
            )
        },
        supportingText = {
            if (value.isBlank()) {
                Text(
                    text = "Obligatorio",
                    color = TextMuted
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPink,
            unfocusedBorderColor = Color(0xFF8D8691),
            focusedLabelColor = PrimaryPink,
            unfocusedLabelColor = TextDark,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark,
            focusedPlaceholderColor = TextMuted,
            unfocusedPlaceholderColor = TextMuted,
            focusedSupportingTextColor = TextMuted,
            unfocusedSupportingTextColor = TextMuted,
            cursorColor = PrimaryPink,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    AppHeladeriaTheme {
        RegisterScreen(
            onRegister = { _, _, _, _ -> },
            onBack = {},
            onGoLogin = {}
        )
    }
}
