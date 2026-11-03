package com.example.aplicacionespa1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aplicacionespa1.ui.theme.AplicacionesPA1Theme
import com.example.aplicacionespa1.ui.components.AppButton
import com.example.aplicacionespa1.ui.theme.SecondaryOrange
class TerminosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionesPA1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TerminosScreen(onAceptarClick = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    })                }
            }
        }
    }
}

@Composable
fun TerminosScreen(onAceptarClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(R.string.terminos_condiciones),
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = stringResource(R.string.texto_terminos),
            modifier = Modifier.padding(vertical = 16.dp),
        )
        AppButton(
            text = stringResource(R.string.aceptar_continuar),
            onClick = onAceptarClick,
            containerColor = SecondaryOrange,
        )
    }
}