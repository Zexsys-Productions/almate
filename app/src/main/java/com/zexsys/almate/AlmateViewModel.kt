package com.zexsys.almate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zexsys.almate.data.GetalmaRepository
import com.zexsys.almate.features.auth.data.CredentialsPreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AppUiState {
    object Loading : AppUiState
    object LoggedIn : AppUiState
    object LoggedOut : AppUiState
}

class AlmateViewModel(
    private val getalmaRepository: GetalmaRepository,
    private val credentialsPreferencesRepository: CredentialsPreferencesRepository
) : ViewModel() {

    var appUiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    private var delayJob: Job? = null

    private val savedSchool: StateFlow<String> =
        credentialsPreferencesRepository.school.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    private val savedUsername: StateFlow<String> =
        credentialsPreferencesRepository.username.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    private val savedPassword: StateFlow<String> =
        credentialsPreferencesRepository.password.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    init {
        viewModelScope.launch {

            combine(
                savedSchool,
                savedUsername,
                savedPassword
            ) {school, username, password -> Triple(school, username, password) }.collect { (school, username, password) ->
                // Cancel any previous delay job
                delayJob?.cancel()

                // Initially set to Loading
                appUiState = AppUiState.Loading

                if (school.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    // Start a delay job to set LoggedOut after 5 seconds
                    delayJob = viewModelScope.launch {
                        delay(500) // Wait for 5 seconds
                        appUiState = AppUiState.LoggedOut
                    }
                } else {
                    // Verify credentials immediately if they are not empty
                    try {
                        val response = getalmaRepository.getVerificationResponse(school, username, password)
                        if (response.authentic) {
                            appUiState = AppUiState.LoggedIn
                        } else {
                            appUiState = AppUiState.LoggedOut
                        }
                    } catch (e: IOException) {
                        appUiState = AppUiState.LoggedOut
                    }
                }
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AlmateApplication)
                AlmateViewModel(
                    getalmaRepository = application.container.getalmaRepository,
                    credentialsPreferencesRepository = application.credentialsPreferencesRepository
                )
            }
        }
    }

}