package com.mankart.mygithubuser.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(private val dataStore: DataStore<Preferences>){
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val USERNAME = stringPreferencesKey("username")

     fun getThemeSetting() : Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
     }

    suspend fun saveThemeSetting(isNightMode: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isNightMode
        }
    }

    fun getUsername() : Flow<String> {
        return dataStore.data.map {
            it[USERNAME] ?: DEFAULT_VAL
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit {
            it[USERNAME] = username
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null
        const val DEFAULT_VAL = "Not set yet"

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}