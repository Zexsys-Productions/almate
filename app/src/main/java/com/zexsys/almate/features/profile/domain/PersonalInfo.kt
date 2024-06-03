package com.zexsys.almate.features.profile.domain

import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo(
    val address: String,
    val districtId: String,
    val email: String,
    val familyNumber: String,
    val lockerNumber: String,
    val lunchNumber: String,
    val name: String,
    val phone: String,
    val preferred: String,
    val schoolId: String,
    val stateId: String
)
