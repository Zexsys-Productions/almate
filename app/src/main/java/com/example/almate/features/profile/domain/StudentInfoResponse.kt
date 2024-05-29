package com.example.almate.features.profile.domain

import kotlinx.serialization.Serializable

@Serializable
data class StudentInfoResponse(
    val address: String,
    val email: String,
    val family: String,
    val locker: String,
    val name: String
)