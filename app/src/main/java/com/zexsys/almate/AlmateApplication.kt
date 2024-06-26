package com.zexsys.almate

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zexsys.almate.data.AppContainer
import com.zexsys.almate.features.auth.data.CredentialsPreferencesRepository
import com.zexsys.almate.data.DefaultAppContainer


private const val CREDENTIAL_PREFERENCE_NAME = "credentials_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CREDENTIAL_PREFERENCE_NAME
)

class AlmateApplication : Application() {
    lateinit var container: AppContainer
    lateinit var credentialsPreferencesRepository: CredentialsPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        credentialsPreferencesRepository = CredentialsPreferencesRepository(dataStore)
    }
}