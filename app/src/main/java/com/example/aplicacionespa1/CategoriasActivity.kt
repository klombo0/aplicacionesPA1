package com.example.aplicacionespa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionespa1.model.Producto
import com.example.aplicacionespa1.network.ApiClient
import com.example.aplicacionespa1.ui.theme.AplicacionesPA1Theme
import coil.compose.AsyncImage
import com.example.aplicacionespa1.ui.components.AppButton
import com.example.aplicacionespa1.ui.components.BackHeader
import com.example.aplicacionespa1.ui.components.SearchField
import com.example.aplicacionespa1.ui.theme.BorderBeige
import com.example.aplicacionespa1.ui.theme.TextDark

class CategoriasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val categoriaInicial = intent.getStringExtra("categoria") ?: "Todos"
        setContent {
            AplicacionesPA1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CategoriasScreen(
                        categoriaInicial = categoriaInicial,
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}
@Composable
fun CategoriasScreen(categoriaInicial: String, onBack: () -> Unit) {
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var categoriaSeleccionada by remember { mutableStateOf(categoriaInicial) }
    var busqueda by remember { mutableStateOf("") }
    var errorCarga by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        ApiClient.obtenerProductos(
            onSuccess = { productos = it },
            onError = { errorCarga = it }
        )
    }
    val filtrados = productos.filter {
        (categoriaSeleccionada == "Todos" || it.categoria == categoriaSeleccionada) &&
                it.nombre.contains(busqueda, ignoreCase = true)
    }
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(36.dp))
        BackHeader(
            title = "Find Your Drink",
            onBack = onBack,
            titleColor = TextDark,
            iconSize = 28.dp,
            iconOffset = (-8).dp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        SearchField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = "Search For Espresso,...",
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listOf("Todos", "Bebidas", "Granos", "Accesorios")) { cat ->
                FilterChip(
                    selected = categoriaSeleccionada == cat,
                    onClick = { categoriaSeleccionada = cat },
                    label = { Text(cat) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4D2303),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("${filtrados.size} Item", color = Color(0xFFB15911), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        if (errorCarga != null) {
            Text(
                "No se pudo cargar el catálogo: $errorCarga",
                color = Color.Red,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                items(filtrados) { producto ->
                    Card(
                        modifier = Modifier.padding(6.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BorderBeige)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            AsyncImage(
                                model = modeloImagenProducto(producto),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(14.dp)),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(producto.nombre, color = Color(0xFF333333), fontWeight = FontWeight.SemiBold)
                            Text(producto.categoria, color = Color(0xFF333333), fontWeight = FontWeight.SemiBold)
                            Text(producto.descripcion, fontSize = 11.sp, color = Color(0xFF565656), maxLines = 2)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("S/ ${producto.precio}", color = Color(0xFF4D2303), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            AppButton(
                                text = "Add To Cart",
                                onClick = {},
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                fontSize = 11.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}