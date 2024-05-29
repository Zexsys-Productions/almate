package com.example.almate.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.almate.AlmateApplication
import com.example.almate.data.GetalmaRepository
import com.example.almate.features.auth.data.CredentialsPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getalmaRepository: GetalmaRepository,
    private val credentialsPreferencesRepository: CredentialsPreferencesRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)

    val savedUsername: StateFlow<String> =
        credentialsPreferencesRepository.username.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    var school by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    fun signIn() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = getalmaRepository.getVerificationResponse(school, username, password)
                if (response.authentic) {
                    credentialsPreferencesRepository.saveSchool(school)
                    credentialsPreferencesRepository.saveUsername(username)
                    credentialsPreferencesRepository.savePassword(password)
                }
                isLoading = false
            } catch (e: IOException) {
                println("Error occurred during logging in.")
                isLoading = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlmateApplication)
                LoginViewModel(
                    getalmaRepository = application.container.getalmaRepository,
                    credentialsPreferencesRepository = application.credentialsPreferencesRepository
                )
            }
        }
    }

}
