package com.interrapidisimo.app.network

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.interrapidisimo.app.models.Localidad
import com.interrapidisimo.app.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ParametrosApiService {

    @GET("api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersion(): Response<JsonElement>

    @GET("api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getEsquema(): Response<JsonElement>

    @GET("api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getLocalidades(): Response<List<Localidad>>
}

interface SeguridadApiService {

    @POST("api/Seguridad/AuthenticaUsuarioApp")
    suspend fun login(
        @HeaderMap headers: Map<String, String>,
        @Body body: LoginRequest
    ): Response<JsonObject>
}
