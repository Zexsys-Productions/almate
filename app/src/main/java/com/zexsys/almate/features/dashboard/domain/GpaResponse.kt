package com.zexsys.almate.features.dashboard.domain

import kotlinx.serialization.Serializable

@Serializable
data class GpaResponse(
    val history: List<History>,
    val live: Double
)