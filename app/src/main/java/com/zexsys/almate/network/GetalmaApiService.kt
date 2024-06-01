package com.zexsys.almate.network

import com.zexsys.almate.features.auth.domain.VerifyResponse
import com.zexsys.almate.features.dashboard.domain.GpaResponse
import com.zexsys.almate.features.dashboard.domain.Grades
import com.zexsys.almate.features.profile.domain.PersonalInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface GetalmaApiService {

    @GET("verify")
    suspend fun getVerificationStatus(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): VerifyResponse

    @GET("grades")
    suspend fun getGrades(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ):  Grades

    @GET("gpa")
    suspend fun getGpa(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): GpaResponse

    @GET("personal-info")
    suspend fun getPersonalInfo(
        @Query("school") school: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): PersonalInfo

}