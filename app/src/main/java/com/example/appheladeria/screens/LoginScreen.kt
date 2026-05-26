package com.example.appheladeria.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFDCEC),
                        Color(0xFFFFF8FC),
                        Color(0xFFEFE8FF)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(170.dp)
                .background(
                    color = PrimaryPink.copy(alpha = 0.12f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(210.dp)
                .background(
                    color = Color.White.copy(alpha = 0.45f),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .size(104.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍧",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bienvenido de nuevo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Ingresa y sigue disfrutando tus sabores favoritos",
                color = TextMuted,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 14.dp,
                        shape = RoundedCornerShape(34.dp)
                    ),
                shape = RoundedCornerShape(34.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.97f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailTouched = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        label = {
                            Text("Correo electrónico")
                        },
                        placeholder = {
                            Text(
                                text = "ejemplo@correo.com",
                                color = TextMuted
                            )
                        },
                        leadingIcon = {
                            Text(
                                text = "📧",
                                style = MaterialTheme.typography.titleMedium
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
                            unfocusedBorderColor = Color(0xFFD8CAD3),
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
                            focusedContainerColor = Color(0xFFFFFBFD),
                            unfocusedContainerColor = Color(0xFFFFFBFD),
                            errorContainerColor = Color(0xFFFFFBFD)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordTouched = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        label = {
                            Text("Contraseña")
                        },
                        placeholder = {
                            Text(
                                text = "Mínimo 6 caracteres",
                                color = TextMuted
                            )
                        },
                        leadingIcon = {
                            Text(
                                text = "🔒",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
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
                            unfocusedBorderColor = Color(0xFFD8CAD3),
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
                            focusedContainerColor = Color(0xFFFFFBFD),
                            unfocusedContainerColor = Color(0xFFFFFBFD),
                            errorContainerColor = Color(0xFFFFFBFD)
                        )
                    )

                    if (generalError.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = PrimaryPink.copy(alpha = 0.10f)
                        ) {
                            Text(
                                text = generalError,
                                modifier = Modifier.padding(
                                    horizontal = 14.dp,
                                    vertical = 10.dp
                                ),
                                color = PrimaryPink,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            emailTouched = true
                            passwordTouched = true
                            onLogin(
                                email.trim(),
                                password.trim()
                            )
                        },
                        enabled = !isLoggingIn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(30.dp),
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
                            Text(
                                text = "Iniciar sesión",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "¿No tienes cuenta? Regístrate",
                        color = PrimaryPink,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable(
                            enabled = !isLoggingIn
                        ) {
                            onGoRegister()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "🍦 Promos, pedidos y momentos dulces en un solo lugar",
                color = TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AppHeladeriaTheme {
        LoginScreen(
            loginError = "",
            isLoggingIn = false,
            onLogin = { _, _ -> },
            onGoRegister = {}
        )
    }
}