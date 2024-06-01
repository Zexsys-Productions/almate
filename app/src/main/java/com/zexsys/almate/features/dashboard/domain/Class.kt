package com.zexsys.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class Class(
    val gradeAsLetter: String,
    val gradeAsPercentage: Int,
    val name: String,
    val teacher: String,
    val url: String,
    val weight: Double
)