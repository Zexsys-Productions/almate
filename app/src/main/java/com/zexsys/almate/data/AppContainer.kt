package com.zexsys.almate.data

import com.zexsys.almate.network.GetalmaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer {
    val getalmaRepository: GetalmaRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://getalma-async.onrender.com"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GetalmaApiService by lazy {
        retrofit.create(GetalmaApiService::class.java)
    }

    override val getalmaRepository: GetalmaRepository by lazy {
        NetworkGetalmaRepository(retrofitService)
    }

}