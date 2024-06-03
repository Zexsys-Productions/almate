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
        overallInfo: OverallInfo
    )
}
class NetworkTopRepository(
    private val database: FirebaseDatabase
) : TopRepository {

    private fun sanitizeUsername(username: String): String {
        return username.replace(Regex("[.#$\\[\\]]"), "_")
    }

    override suspend fun upsertUser(overallInfo: OverallInfo) {
        val ref = database.getReference("users/${sanitizeUsername(overallInfo.username)}")
        ref.setValue(overallInfo)
    }

    override suspend fun deleteUser(overallInfo: OverallInfo) {
        val ref = database.getReference("users/${sanitizeUsername(overallInfo.username)}")
        ref.removeValue()
    }

}