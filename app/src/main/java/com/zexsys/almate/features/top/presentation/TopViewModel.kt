package com.zexsys.almate.features.top.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Exclude
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.zexsys.almate.AlmateApplication
import com.zexsys.almate.features.top.data.TopRepository
import com.zexsys.almate.features.top.domain.OverallInfo
import kotlinx.coroutines.launch

sealed interface TopUiState {
    object Success : TopUiState
    object Error : TopUiState
    object Loading : TopUiState
}

// TODO add repository
class TopViewModel() : ViewModel() {

    var listOfUsers: List<OverallInfo>? by mutableStateOf(null)

    val options = listOf("Everyone", "Custom")
    var selectedIndex = mutableIntStateOf(0)

    // firebase realtime database
    private val database = Firebase.database.reference

    val usersListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val updatedList = mutableListOf<OverallInfo>()
            for (userSnapshot in dataSnapshot.children) {
                val user = userSnapshot.getValue<OverallInfo>()
                user?.let { updatedList.add(it) }
            }
            listOfUsers = updatedList
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "loadUsers:onCancelled", databaseError.toException())
        }
    }

    init {
        database.child("users").addValueEventListener(usersListener)
        listOfUsers?.sortedByDescending { it.rankedRating }
    }

}