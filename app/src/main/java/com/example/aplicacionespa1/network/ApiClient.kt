package com.example.aplicacionespa1.network

import com.example.aplicacionespa1.model.AuthResponse
import com.example.aplicacionespa1.model.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("productos.php")
    fun obtenerProductos(): Call<List<Producto>>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("registro.php")
    fun registro(
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>
}

object ApiClient {
    private const val BASE_URL = "https://klombo.alwaysdata.net/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private fun <T> Call<T>.ejecutar(onSuccess: (T) -> Unit, onError: (String) -> Unit) {
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    onSuccess(body)
                } else {
                    onError("Error del servidor: código ${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError(t.message ?: "No se pudo conectar al servicio")
            }
        })
    }

    fun obtenerProductos(onSuccess: (List<Producto>) -> Unit, onError: (String) -> Unit) =
        apiService.obtenerProductos().ejecutar(onSuccess, onError)

    fun login(email: String, password: String, onSuccess: (AuthResponse) -> Unit, onError: (String) -> Unit) =
        apiService.login(email, password).ejecutar(onSuccess, onError)

    fun registrar(nombre: String, email: String, password: String, onSuccess: (AuthResponse) -> Unit, onError: (String) -> Unit) =
        apiService.registro(nombre, email, password).ejecutar(onSuccess, onError)
}