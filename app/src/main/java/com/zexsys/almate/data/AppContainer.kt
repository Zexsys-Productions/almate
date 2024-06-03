package com.zexsys.almate.data

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.zexsys.almate.network.GetalmaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zexsys.almate.features.top.data.NetworkTopRepository
import com.zexsys.almate.features.top.data.TopRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer {
    val getalmaRepository: GetalmaRepository
    val topRepository: TopRepository
}

class DefaultAppContainer : AppContainer {

    // getalma-async-v2 API
    private val baseUrl = "https://getalma-async-v2.onrender.com"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
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

    // firebase
    private val database = Firebase.database

    override val topRepository: TopRepository by lazy {
        NetworkTopRepository(database)
    }

}