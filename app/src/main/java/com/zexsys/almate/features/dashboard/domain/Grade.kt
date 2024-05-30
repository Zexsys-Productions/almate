package com.zexsys.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class Grade(
    val assignments: List<Assignment>,
    val categories: List<Category>,
    val gradeAsLetter: String,
    val gradeAsPercentage: String,
    val name: String,
    val teacher: String,
    val weight: String
)