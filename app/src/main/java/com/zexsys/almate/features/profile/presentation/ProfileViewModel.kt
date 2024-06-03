package com.zexsys.almate.features.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zexsys.almate.AlmateApplication
import com.zexsys.almate.data.GetalmaRepository
import com.zexsys.almate.features.auth.data.CredentialsPreferencesRepository
import com.zexsys.almate.features.profile.domain.PersonalInfo
import com.zexsys.almate.features.top.data.TopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ProfileUiState {
    data class Success(val personalInfo: PersonalInfo) : ProfileUiState
    object Error : ProfileUiState
    object Loading : ProfileUiState
}

class ProfileViewModel(
    private val getalmaRepository: GetalmaRepository,
    private val topRepository: TopRepository,
    private val credentialsPreferencesRepository: CredentialsPreferencesRepository
) : ViewModel() {

    var profileUiState: ProfileUiState by mutableStateOf(ProfileUiState.Loading)
        private set

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
        getStudentInfo()
    }

    fun getStudentInfo() {
        viewModelScope.launch {

            profileUiState = ProfileUiState.Loading

            combine(
                savedSchool,
                savedUsername,
                savedPassword
            ) {school, username, password -> Triple(school, username, password) }.collect { (school, username, password) ->

                profileUiState = if (school.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                    try {
                        val response = getalmaRepository.getPersonalInfo(school = school, username = username, password = password)
                        ProfileUiState.Success(personalInfo = response)
                    } catch (e: IOException) {
                        ProfileUiState.Error
                    }
                } else {
                    ProfileUiState.Loading
                }
            }

        }
    }

    fun logOut() {
        viewModelScope.launch {
            try {
                topRepository.deleteUser(savedUsername.value)
            } catch (e: Exception) {
                println("failed to delete user from users database.")
            }
            credentialsPreferencesRepository.saveSchool("")
            credentialsPreferencesRepository.saveUsername("")
            credentialsPreferencesRepository.savePassword("")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlmateApplication)
                ProfileViewModel(
                    getalmaRepository = application.container.getalmaRepository,
                    topRepository = application.container.topRepository,
                    credentialsPreferencesRepository = application.credentialsPreferencesRepository
                )
            }
        }
    }

}