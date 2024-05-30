package com.zexsys.almate.data

import com.zexsys.almate.features.auth.domain.VerifyResponse
import com.zexsys.almate.network.GetalmaApiService
import com.zexsys.almate.features.dashboard.domain.GradeInfoResponse
import com.zexsys.almate.features.profile.domain.StudentInfoResponse

interface GetalmaRepository {

    suspend fun getVerificationResponse(
        school: String,
        username: String,
        password: String,
    ): VerifyResponse

    suspend fun getCurrentGradeInfo(
        school: String,
        username: String,
        password: String,
    ): GradeInfoResponse


    suspend fun getStudentInfo(
        school: String,
        username: String,
        password: String,
    ): StudentInfoResponse

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

    override suspend fun getCurrentGradeInfo(
        school: String,
        username: String,
        password: String
    ): GradeInfoResponse {
        return getalmaApiService.getCurrentGradeInfo(school, username, password)
    }

    override suspend fun getStudentInfo(
        school: String,
        username: String,
        password: String
    ): StudentInfoResponse {
        return getalmaApiService.getStudentInfo(school, username, password)
    }

}