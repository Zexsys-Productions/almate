package com.zexsys.almate.data

import com.zexsys.almate.features.auth.domain.VerifyResponse
import com.zexsys.almate.features.dashboard.domain.GpaResponse
import com.zexsys.almate.features.dashboard.domain.Grades
import com.zexsys.almate.network.GetalmaApiService
import com.zexsys.almate.features.profile.domain.PersonalInfo

interface GetalmaRepository {

    suspend fun getVerificationResponse(
        school: String,
        username: String,
        password: String,
    ): VerifyResponse

    suspend fun getGrades(
        school: String,
        username: String,
        password: String,
    ): Grades

    suspend fun getGpa(
        school: String,
        username: String,
        password: String,
    ): GpaResponse

    suspend fun getPersonalInfo(
        school: String,
        username: String,
        password: String,
    ): PersonalInfo

}

class NetworkGetalmaRepository(
    private val getalmaApiService: GetalmaApiService
) : GetalmaRepository {

    override suspend fun getVerificationResponse(
        school: String,
        username: String,
        password: String,
    ): VerifyResponse {
        return getalmaApiService.getVerificationStatus(school, username, password)
    }

    override suspend fun getGrades(
        school: String,
        username: String,
        password: String
    ): Grades {
        return getalmaApiService.getGrades(school, username, password)
    }

    override suspend fun getGpa(
        school: String,
        username: String,
        password: String
    ): GpaResponse {
        return getalmaApiService.getGpa(school, username, password)
    }

    override suspend fun getPersonalInfo(
        school: String,
        username: String,
        password: String
    ): PersonalInfo {
        return getalmaApiService.getPersonalInfo(school, username, password)
    }

}