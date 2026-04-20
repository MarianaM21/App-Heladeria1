package com.example.appheladeria.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted

@Composable
fun LoginScreen(
    loginError: String,
    isLoggingIn: Boolean,
    onLogin: (String, String) -> Unit,
    onGoRegister: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailTouched by rememberSaveable { mutableStateOf(false) }
    var passwordTouched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(loginError) {
        if (loginError.isNotBlank()) {
            emailTouched = true
            passwordTouched = true
        }
    }

    val emailError = when {
        !emailTouched -> ""
        email.isBlank() -> "Obligatorio"
        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Correo no válido"
        loginError == "Correo incorrecto" -> "Correo incorrecto"
        else -> ""
    }

    val passwordError = when {
        !passwordTouched -> ""
        password.isBlank() -> "Obligatorio"
        loginError == "Contraseña incorrecta" -> "Contraseña incorrecta"
        else -> ""
    }

    val generalError = when (loginError) {
        "Primero debes crear una cuenta",
        "Todos los campos son obligatorios" -> loginError
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .statusBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hola de nuevo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Inicia sesión para continuar",
                    color = TextMuted,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailTouched = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(22.dp),
                    label = { Text("Correo electrónico") },
                    placeholder = {
                        Text(
                            text = "Obligatorio",
                            color = TextMuted
                        )
                    },
                    isError = emailError.isNotBlank(),
                    supportingText = {
                        if (emailError.isNotBlank()) {
                            Text(
                                text = emailError,
                                color = PrimaryPink
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPink,
                        unfocusedBorderColor = Color(0xFF8D8691),
                        errorBorderColor = PrimaryPink,
                        focusedLabelColor = PrimaryPink,
                        unfocusedLabelColor = TextDark,
                        errorLabelColor = PrimaryPink,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark,
                        errorTextColor = TextDark,
                        focusedPlaceholderColor = TextMuted,
                        unfocusedPlaceholderColor = TextMuted,
                        errorPlaceholderColor = TextMuted,
                        focusedSupportingTextColor = TextMuted,
                        unfocusedSupportingTextColor = TextMuted,
                        errorSupportingTextColor = PrimaryPink,
                        cursorColor = PrimaryPink,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        errorContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordTouched = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(22.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Contraseña") },
                    placeholder = {
                        Text(
                            text = "Obligatorio",
                            color = TextMuted
                        )
                    },
                    isError = passwordError.isNotBlank(),
                    supportingText = {
                        if (passwordError.isNotBlank()) {
                            Text(
                                text = passwordError,
                                color = PrimaryPink
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPink,
                        unfocusedBorderColor = Color(0xFF8D8691),
                        errorBorderColor = PrimaryPink,
                        focusedLabelColor = PrimaryPink,
                        unfocusedLabelColor = TextDark,
                        errorLabelColor = PrimaryPink,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark,
                        errorTextColor = TextDark,
                        focusedPlaceholderColor = TextMuted,
                        unfocusedPlaceholderColor = TextMuted,
                        errorPlaceholderColor = TextMuted,
                        focusedSupportingTextColor = TextMuted,
                        unfocusedSupportingTextColor = TextMuted,
                        errorSupportingTextColor = PrimaryPink,
                        cursorColor = PrimaryPink,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        errorContainerColor = Color.White
                    )
                )

                if (generalError.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = generalError,
                        color = PrimaryPink,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        emailTouched = true
                        passwordTouched = true
                        onLogin(email.trim(), password.trim())
                    },
                    enabled = !isLoggingIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPink,
                        disabledContainerColor = PrimaryPink.copy(alpha = 0.5f),
                        disabledContentColor = Color.White
                    )
                ) {
                    if (isLoggingIn) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.5.dp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text("Validando...")
                        }
                    } else {
                        Text("Iniciar sesión")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(enabled = !isLoggingIn) {
                        onGoRegister()
                    }
                )
            }
        }
    }
}