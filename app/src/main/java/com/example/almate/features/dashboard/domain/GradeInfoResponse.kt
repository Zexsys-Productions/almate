package com.example.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class GradeInfoResponse(
    val gpa: String,
    val grades: List<Grade>
)