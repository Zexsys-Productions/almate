package com.zexsys.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val category: String,
    val gradeAsLetter: String,
    val gradeAsPercentage: String,
    val name: String,
    val percentageOfGrade: String,
    val updated: String
)