package com.example.aplicacionespa1.model

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int = 0,
    val disponible: Int = 1,
    val imagen: String
)