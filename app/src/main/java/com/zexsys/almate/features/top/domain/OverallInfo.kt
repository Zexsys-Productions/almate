package com.zexsys.almate.features.top.domain

import kotlinx.serialization.Serializable

@Serializable
data class OverallInfo(
    val amountOfAPlus: Int,
    val gpa: String,
    val rankedRating: Int,
    val username: String
)