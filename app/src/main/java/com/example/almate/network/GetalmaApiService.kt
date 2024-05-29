package com.example.almate.network

import com.example.almate.features.auth.domain.VerifyResponse
import com.example.almate.features.dashboard.domain.GradeInfoResponse
import com.example.almate.features.profile.domain.StudentInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetalmaApiService {

    @GET("verify")
    suspend fun getVerificationStatus(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): VerifyResponse

    @GET("currentgradeinfo")
    suspend fun getCurrentGradeInfo(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): GradeInfoResponse

    @GET("studentinfo")
    suspend fun getStudentInfo(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): StudentInfoResponse

}