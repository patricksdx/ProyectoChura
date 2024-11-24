package com.sandur.proyectochura.utils.userstore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Configuración del DataStore
private const val USER_DATASTORE_NAME = "user_preferences"

// Extension para inicializar el DataStore
private val Context.userDataStore by preferencesDataStore(name = USER_DATASTORE_NAME)

// Clase UserStore
class UserStore(private val context: Context) {
    private val dataStore = context.userDataStore

    // Obtener el ID del usuario actual como Flow
    val currentUserId: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.CURRENT_USER_ID]
    }

    // Verificar si la sesión debe persistir
    val isSessionPersistent: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SESSION_PERSISTENCE] ?: false
    }

    // Guardar el ID del usuario
    suspend fun saveCurrentUser(userId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_USER_ID] = userId
        }
    }

    // Borrar el ID del usuario
    suspend fun clearCurrentUser() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.CURRENT_USER_ID)
        }
    }

    // Guardar si la sesión debe persistir
    suspend fun setSessionPersistence(isPersistent: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSION_PERSISTENCE] = isPersistent
        }
    }
}

// Claves de preferencias
private object PreferencesKeys {
    val CURRENT_USER_ID = intPreferencesKey("current_user_id")
    val SESSION_PERSISTENCE = booleanPreferencesKey("session_persistence")
}