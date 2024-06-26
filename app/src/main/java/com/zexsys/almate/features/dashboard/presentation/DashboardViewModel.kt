package com.zexsys.almate.features.dashboard.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zexsys.almate.AlmateApplication
import com.zexsys.almate.data.GetalmaRepository
import com.zexsys.almate.features.auth.data.CredentialsPreferencesRepository
import com.zexsys.almate.features.dashboard.domain.Class
import com.zexsys.almate.features.dashboard.domain.GpaResponse
import com.zexsys.almate.features.dashboard.domain.Grades
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface DashboardUiState {
    data class Success(val classes: List<Class>, val gpaResponse: GpaResponse) : DashboardUiState
    object Error : DashboardUiState
    object Loading : DashboardUiState
}

class DashboardViewModel(
    private val getalmaRepository: GetalmaRepository,
    private val credentialsPreferencesRepository: CredentialsPreferencesRepository
) : ViewModel() {

    var dashboardUiState: DashboardUiState by mutableStateOf(DashboardUiState.Loading)
        private set

    var classes: List<Class> by mutableStateOf(emptyList())
        private set

    var gpa: GpaResponse by mutableStateOf(GpaResponse(emptyList(), ""))
        private set

    var sortedAlphabetically by mutableStateOf(true)

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

    fun switchSort() {
        if (sortedAlphabetically) {
            sortedAlphabetically = !sortedAlphabetically
            dashboardUiState = DashboardUiState.Success(classes.sortedByDescending { it.gradeAsPercentage }, gpa)
        } else {
            sortedAlphabetically = !sortedAlphabetically
            dashboardUiState = DashboardUiState.Success(classes.sortedByDescending { it.name }, gpa)
        }
    }

    init {
        getDashboardInfo()
    }

    fun getDashboardInfo() {

        dashboardUiState = DashboardUiState.Loading

        viewModelScope.launch {

            combine(
                savedSchool,
                savedUsername,
                savedPassword
            ) {school, username, password -> Triple(school, username, password) }.collect { (school, username, password) ->

                dashboardUiState = if (school.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                    try {
                        coroutineScope {
                            val gradesDeferred = async { getalmaRepository.getGrades(school = school, username = username, password = password) }
                            val gpaDeferred = async { getalmaRepository.getGpa(school = school, username = username, password = password) }

                            val gradesPromise = gradesDeferred.await()
                            val gpaPromise = gpaDeferred.await()

                            val sortedClasses = gradesPromise.classes.sortedBy { it.name }

                            classes = gradesPromise.classes
                            gpa = gpaPromise
                            DashboardUiState.Success(sortedClasses, gpaPromise)
                        }
                    } catch (e: IOException) {
                        DashboardUiState.Error
                    }
                } else {
                    DashboardUiState.Loading
                }
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AlmateApplication)
                DashboardViewModel(
                    getalmaRepository = application.container.getalmaRepository,
                    credentialsPreferencesRepository = application.credentialsPreferencesRepository
                )
            }
        }
    }

}