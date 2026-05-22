package com.interrapidisimo.app.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL_PARAMETROS =
        "https://apitesting.interrapidisimo.co/apicontrollerpruebas/"

    private const val BASE_URL_SEGURIDAD =
        "https://apitesting.interrapidisimo.co/FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val parametrosService: ParametrosApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_PARAMETROS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ParametrosApiService::class.java)
    }

    val seguridadService: SeguridadApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_SEGURIDAD)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SeguridadApiService::class.java)
    }
}
