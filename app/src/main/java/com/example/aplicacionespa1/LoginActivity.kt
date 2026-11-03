package com.example.aplicacionespa1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aplicacionespa1.network.ApiClient
import com.example.aplicacionespa1.ui.components.*
import com.example.aplicacionespa1.ui.theme.AplicacionesPA1Theme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionesPA1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(
                        onBack = { finish() },
                        onLoginOk = {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        },
                        onIrSignUp = {
                            startActivity(Intent(this, SignUpActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onBack: () -> Unit, onLoginOk: () -> Unit, onIrSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf<String?>(null) }
    var errorPassword by remember { mutableStateOf<String?>(null) }

    var mostrarOlvide by remember { mutableStateOf(false) }
    var emailRecuperar by remember { mutableStateOf("") }
    var errorRecuperar by remember { mutableStateOf<String?>(null) }

    AuthScreen(title = "¡Bienvenido de nuevo!", onBack = onBack) {
        AuthTextField(
            label = "Correo Electrónico",
            value = email,
            onValueChange = { email = it; errorEmail = null },
            placeholder = "Tu email",
            keyboardType = KeyboardType.Email,
            error = errorEmail
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            label = "Contraseña",
            value = password,
            onValueChange = { password = it; errorPassword = null },
            placeholder = "",
            isPassword = true,
            error = errorPassword
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinkText("¿Olvidaste tu contraseña?", onClick = { mostrarOlvide = true })

        Spacer(modifier = Modifier.weight(1f))

        AppButton("Iniciar Sesión", onClick = {
            errorEmail = if (!email.contains("@")) "Ingresa un correo válido" else null
            errorPassword = if (password.length < 6) "Mínimo 6 caracteres" else null
            if (errorEmail == null && errorPassword == null) {
                ApiClient.login(
                    email = email.trim(),
                    password = password,
                    onSuccess = { respuesta ->
                        if (respuesta.ok) onLoginOk() else errorPassword = respuesta.mensaje
                    },
                    onError = { mensaje ->
                        errorPassword = mensaje
                    }
                )
            }
        })
        Spacer(modifier = Modifier.height(24.dp))
        DividerWithText("O ingresa con")
        Spacer(modifier = Modifier.height(16.dp))
        SocialLoginRow(
            googleIcon = R.drawable.ic_google,
            facebookIcon = R.drawable.ic_facebook,
            onGoogle = {},
            onFacebook = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthFooterLink("¿No tienes una cuenta?", "Regístrate", onIrSignUp)
    }

    if (mostrarOlvide) {
        AppModal(
            title = "¿Olvidaste tu contraseña?",
            subtitle = "Escribe tu correo electrónico para recibir un código de confirmación y restablecer tu contraseña.",
            onDismiss = { mostrarOlvide = false }
        ) {
            AuthTextField(
                label = "Correo Electrónico",
                value = emailRecuperar,
                onValueChange = { emailRecuperar = it; errorRecuperar = null },
                placeholder = "usuario1@gmail.com",
                keyboardType = KeyboardType.Email,
                error = errorRecuperar
            )
            Spacer(modifier = Modifier.height(24.dp))
            AppButton("Confirmar Correo", onClick = {
                if (!emailRecuperar.contains("@")) {
                    errorRecuperar = "Ingresa un correo válido"
                } else {
                    mostrarOlvide = false
                }
            })
        }
    }
}