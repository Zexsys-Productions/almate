package com.zexsys.almate.features.auth.presentation.login

import android.app.Application
import android.widget.Toast
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
import com.zexsys.almate.AlmateApplication
import com.zexsys.almate.data.GetalmaRepository
import com.zexsys.almate.features.auth.data.CredentialsPreferencesRepository
import com.zexsys.almate.features.top.data.TopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val getalmaRepository: GetalmaRepository,
    private val topRepository: TopRepository,
    private val credentialsPreferencesRepository: CredentialsPreferencesRepository
) : ViewModel() {

    var signInEnabled by mutableStateOf(false)
    var openLoadingDialog by mutableStateOf(false)
    var errorLoggingIn by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var school by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onSchoolChange(newSchool: String) {
        school = newSchool
        updateLoginEnabled()
    }

    fun onUsernameChange(newUsername: String) {
        username = newUsername
        updateLoginEnabled()
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        updateLoginEnabled()
    }

    private fun updateLoginEnabled() {
        signInEnabled = school.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
    }

    fun signIn() {
        viewModelScope.launch {
            openLoadingDialog = true
            try {
                val response = getalmaRepository.getVerificationResponse(school, username, password)
                if (response.authentic == 302) {
                    credentialsPreferencesRepository.saveSchool(school)
                    credentialsPreferencesRepository.saveUsername(username)
                    credentialsPreferencesRepository.savePassword(password)
                    try {
                        val overallInfo = getalmaRepository.getOverallInfo(school, username, password)
                        topRepository.upsertUser(overallInfo)
                    } catch (e: IOException) {
                        println("something went wrong with the HTTP request when upserting: IO")
                    } catch (e: HttpException) {
                        println("something went wrong with the HTTP request when upserting: HTTP")
                    } catch (e: Exception) {
                        println("something went wrong in general with the upserting of the users overall information")
                    }
                } else if (response.authentic == 500) {
                    errorMessage = "Invalid credentials!"
                    errorLoggingIn = true
                } else if (response.authentic == 301) {
                    errorMessage = "Invalid school!"
                    errorLoggingIn = true
                } else {
                    errorMessage = "Something terrible went wrong."
                    errorLoggingIn = true
                }
                openLoadingDialog = false
            } catch (e: IOException) {
                openLoadingDialog = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlmateApplication)
                LoginViewModel(
                    getalmaRepository = application.container.getalmaRepository,
                    topRepository = application.container.topRepository,
                    credentialsPreferencesRepository = application.credentialsPreferencesRepository
                )
            }
        }
    }

}
