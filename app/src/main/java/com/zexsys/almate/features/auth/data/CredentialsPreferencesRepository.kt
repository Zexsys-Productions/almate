package com.zexsys.almate.features.auth.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class CredentialsPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val SCHOOL = stringPreferencesKey("school")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        const val TAG = "CredentialsPreferencesRepository"
    }

    val school: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading credentials.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SCHOOL] ?: ""
        }

    suspend fun saveSchool(school: String) {
        dataStore.edit { preferences ->
            preferences[SCHOOL] = school
        }
    }

    val username: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading credentials.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USERNAME] ?: ""
        }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    val password: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading credentials.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PASSWORD] ?: ""
        }

    suspend fun savePassword(password: String) {
        dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

}