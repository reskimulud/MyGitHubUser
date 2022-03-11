package com.mankart.mygithubuser.viewmodel

import androidx.lifecycle.*
import com.mankart.mygithubuser.data.datastore.SettingPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreference): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isNightMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isNightMode)
        }
    }

    fun getUsername() : LiveData<String> {
        return pref.getUsername().asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }
}