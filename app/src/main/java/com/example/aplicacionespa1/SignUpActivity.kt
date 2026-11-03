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

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionesPA1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SignUpScreen(
                        onBack = { finish() },
                        onRegistroOk = {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        },
                        onIrLogin = {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(onBack: () -> Unit, onRegistroOk: () -> Unit, onIrLogin: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorEmail by remember { mutableStateOf<String?>(null) }
    var errorPassword by remember { mutableStateOf<String?>(null) }

    AuthScreen(title = "¡Comienza Ahora!", onBack = onBack) {
        AuthTextField(
            label = "Nombre",
            value = nombre,
            onValueChange = { nombre = it; errorNombre = null },
            placeholder = "Tu nombre",
            error = errorNombre
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            placeholder = "Mínimo 6 caracteres",
            isPassword = true,
            error = errorPassword
        )

        Spacer(modifier = Modifier.weight(1f))

        AppButton("Registrarse", onClick = {
            errorNombre = if (nombre.isBlank()) "Ingresa tu nombre" else null
            errorEmail = if (!email.contains("@")) "Ingresa un correo válido" else null
            errorPassword = if (password.length < 6) "Mínimo 6 caracteres" else null
            if (errorNombre == null && errorEmail == null && errorPassword == null) {
                ApiClient.registrar(
                    nombre = nombre.trim(),
                    email = email.trim(),
                    password = password,
                    onSuccess = { respuesta ->
                        if (respuesta.ok) onRegistroOk() else errorEmail = respuesta.mensaje
                    },
                    onError = { mensaje ->
                        errorPassword = mensaje
                    }
                )
            }
        })
        Spacer(modifier = Modifier.height(24.dp))
        DividerWithText("O regístrate con")
        Spacer(modifier = Modifier.height(16.dp))
        SocialLoginRow(
            googleIcon = R.drawable.ic_google,
            facebookIcon = R.drawable.ic_facebook,
            onGoogle = {},
            onFacebook = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthFooterLink("¿Ya tienes una cuenta?", "Iniciar Sesión", onIrLogin)
    }
}