package com.zexsys.almate.features.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class VerifyResponse(
    val authentic: Int
)