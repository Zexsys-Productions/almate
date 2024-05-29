package com.example.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val gradeAsLetter: String,
    val gradeAsPercentage: String,
    val name: String,
    val weightAsPercentage: String
)