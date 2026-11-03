package com.example.aplicacionespa1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionespa1.model.Producto
import com.example.aplicacionespa1.network.ApiClient
import com.example.aplicacionespa1.ui.theme.AplicacionesPA1Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.filled.AccountCircle
import com.example.aplicacionespa1.ui.components.SearchField

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionesPA1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(
                        onCategoriaClick = { categoria ->
                            val i = Intent(this, CategoriasActivity::class.java)
                            i.putExtra("categoria", categoria)
                            startActivity(i)
                        },
                        onVerPerfil = {
                            startActivity(Intent(this, SignUpActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onCategoriaClick: (String) -> Unit,
               onVerPerfil: () -> Unit) {
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var errorCarga by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        ApiClient.obtenerProductos(
            onSuccess = { productos = it },
            onError = { errorCarga = it }
        )
    }
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = true, onClick = {},
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4D2303),
                        selectedTextColor = Color(0xFF4D2303),
                        unselectedIconColor = Color(0xFF565656),
                        unselectedTextColor = Color(0xFF565656),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = {},
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favourite") },
                    label = { Text("Favourite") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF565656),
                        unselectedTextColor = Color(0xFF565656)
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = {},
                    icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart") },
                    label = { Text("Cart") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF565656),
                        unselectedTextColor = Color(0xFF565656)
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = onVerPerfil,
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF565656),
                        unselectedTextColor = Color(0xFF565656)
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Encabezado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFF5EDE4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Foto de perfil",
                        tint = Color(0xFF4D2303),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Hello!", fontSize = 13.sp, color = Color(0xFF565656))
                    Text("Bienvenido a MaZaj", fontWeight = FontWeight.SemiBold, color = Color(0xFF333333))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SearchField(
                value = "",
                onValueChange = {},
                placeholder = "Search Drinks, Beans,...",
            )
            errorCarga?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("No se pudo cargar el catálogo: $it", color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Categories", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF333333))
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CategoriaCircle("Our Drinks", R.drawable.cafes) { onCategoriaClick("Bebidas") }
                CategoriaCircle("Coffee Beans", R.drawable.granos_cafe) { onCategoriaClick("Granos") }
                CategoriaCircle("Coffee Tools", R.drawable.accesorios) { onCategoriaClick("Accesorios") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.rewards_banner),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {Text(text = "¡20% de Descuento en todo Espresso!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                    )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Solo por hoy para ti",
                            color = Color(0xFFE0C9B0),
                            fontSize = 12.sp
                        )
                    }
                    Button(onClick = { onCategoriaClick("Bebidas") },
                        modifier = Modifier.height(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB15911)
                        ))
                    { Text(text = "Pedir Ahora",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Recently Reorder", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF333333))
                TextButton(onClick = { onCategoriaClick("Todos") }) { Text("See All", color = Color(0xFFB15911)) }
            }
            Spacer(modifier = Modifier.height(8.dp))

            productos.take(2).forEach { producto ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EDE4))
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = modeloImagenProducto(producto), contentDescription = producto.nombre,
                            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(producto.nombre, fontWeight = FontWeight.SemiBold, color = Color(0xFF333333))
                            Text(producto.descripcion, fontSize = 11.sp, color = Color(0xFF565656), maxLines = 1)
                            Text("S/ ${producto.precio}", fontWeight = FontWeight.Bold, color = Color(0xFF4D2303))
                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4D2303))
                        ) { Text("Reorder", fontSize = 11.sp) }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEDE0D4))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "¿Cómo te sientes hoy?",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "Deja que nuestro Barista IA encuentre la taza perfecta según tu ánimo.",
                        fontSize = 12.sp,
                        color = Color(0xFF565656)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MoodButton("Necesito Enfoque")
                        MoodButton("Necesito Energía")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MoodButton("Necesito Relax")
                        MoodButton("Algo Dulce")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun CategoriaCircle(nombre: String, imagen: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Image(
            painter = painterResource(imagen),
            contentDescription = nombre,
            modifier = Modifier.size(70.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(nombre, fontSize = 11.sp, color = Color(0xFF333333))
    }
}

@Composable
fun MoodButton(texto: String) {
    OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
        Text(texto, fontSize = 12.sp, color = Color(0xFF333333))
    }
}


private const val URL_BASE_IMAGENES = "https://klombo.alwaysdata.net/"
fun modeloImagenProducto(producto: Producto): Any {
    return URL_BASE_IMAGENES + producto.imagen
}