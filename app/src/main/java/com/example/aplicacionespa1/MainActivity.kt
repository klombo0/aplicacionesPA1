package com.example.aplicacionespa1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionespa1.ui.theme.AplicacionesPA1Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionesPA1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    ) {

                    MainScreen {
                        startActivity(Intent(this, TerminosActivity::class.java))
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onTerminosClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.fondo),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                color = Color(0xFF4D2303),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Coffee, Your Way",
                fontSize = 16.sp,
                color = Color(0xFFB15911),
            )
        }

        Button(
            onClick = onTerminosClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp)
                .fillMaxWidth(0.7f)
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB15911),
            ),
        ) {
            Text(
                text = stringResource(R.string.empezar),
                color = Color.White,
                fontSize = 28.sp,
            )
        }
    }
}