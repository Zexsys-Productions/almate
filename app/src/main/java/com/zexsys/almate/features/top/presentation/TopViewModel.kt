package com.zexsys.almate.features.top.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.zexsys.almate.features.top.domain.OverallInfo

sealed interface TopUiState {
    object Success : TopUiState
    object Error : TopUiState
    object Loading : TopUiState
}

// TODO add repository
class TopViewModel : ViewModel() {

    var topUiState: TopUiState by mutableStateOf(TopUiState.Loading)
        private set

    val options = mutableStateListOf<String>("Global", "Custom")
    var selectedIndex = mutableIntStateOf(0)

    var text = mutableStateOf("plceholder")

    private fun sanitizeUsername(username: String): String {
        return username.replace(Regex("[.#$\\[\\]]"), "_")
    }

    // firebase realtime database
    private val database = Firebase.database

    fun upsertUser(overallInfo: OverallInfo) {
        val ref = database.getReference("users/${sanitizeUsername(overallInfo.username)}")
        ref.setValue(overallInfo)
    }

    fun deleteUser(overallInfo: OverallInfo) {
        val ref = database.getReference("users/${sanitizeUsername(overallInfo.username)}")
        ref.removeValue()
    }

}