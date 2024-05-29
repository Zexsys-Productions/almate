package com.example.almate.features.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class VerifyResponse(
    val authentic: Boolean
)