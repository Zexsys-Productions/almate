package com.zexsys.almate.features.top.domain

import kotlinx.serialization.Serializable

@Serializable
data class OverallInfo(
    val amountOfAPlus: Int = 0,
    val gpa: String = "",
    val rankedRating: Int = 0,
    val username: String = ""
)