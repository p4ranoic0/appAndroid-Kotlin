package com.isil.ep_2.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore (private val context: Context) {
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datos")
        val DATOS_USUARIO = stringPreferencesKey("datos_usuario")
    }

    val getDatosUsuario: Flow<String> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[DATOS_USUARIO] ?: ""
        }

    suspend fun setDatosUsuario(data: String) {
        context.dataStore.edit {
                it[DATOS_USUARIO] = data
        }
    }
}