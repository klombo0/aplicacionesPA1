package com.example.aplicacionespa1.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val rol: String
)

data class AuthResponse(
    val ok: Boolean,
    val mensaje: String,
    val usuario: Usuario? = null
)
