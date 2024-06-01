package com.zexsys.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class History(
    val grade: Int,
    val value: Double
)