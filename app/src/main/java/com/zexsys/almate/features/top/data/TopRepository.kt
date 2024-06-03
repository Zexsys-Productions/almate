package com.zexsys.almate.features.top.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.zexsys.almate.features.top.domain.OverallInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface TopRepository {
    suspend fun upsertUser(
        overallInfo: OverallInfo
    )
    suspend fun deleteUser(
        username: String
    )

    suspend fun updateProfilePicture(
        username: String,
        imageUrl: String
    )

}
class NetworkTopRepository(
    private val database: FirebaseDatabase
) : TopRepository {

    private val dbReference = database.reference

    private fun sanitizeUsername(username: String): String {
        return username.replace(Regex("[.#$\\[\\]]"), "_")
    }

    override suspend fun upsertUser(overallInfo: OverallInfo) {
        val ref = dbReference.child("users").child(sanitizeUsername(overallInfo.username))
        ref.setValue(overallInfo)
    }

    override suspend fun deleteUser(username: String) {
        val ref = dbReference.child("users").child(sanitizeUsername(username))
        ref.removeValue()
    }

    override suspend fun updateProfilePicture(username: String, imageUrl: String) {
        TODO("Not yet implemented")
    }

}