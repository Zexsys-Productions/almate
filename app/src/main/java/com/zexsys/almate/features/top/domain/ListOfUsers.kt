package com.zexsys.almate.features.top.domain

import kotlinx.serialization.Serializable

@Serializable
data class ListOfUsers(
    val users: List<OverallInfo>
)