package com.example.cryptomatthew.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cryptomatthew.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    @ApplicationContext val context: Context
) {
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val NOTIFICATIONS_TIME = stringPreferencesKey("notifications_time")
    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }
    }

    fun getNotificationsEnabled(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[NOTIFICATIONS_ENABLED] ?: false
            }
    }

    suspend fun saveNotificationsTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_TIME] = time
        }
    }

    fun getNotificationsTime(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[NOTIFICATIONS_TIME] ?: "12:00"
            }
    }
}