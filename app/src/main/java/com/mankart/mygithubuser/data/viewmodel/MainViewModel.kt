package com.mankart.mygithubuser.data.viewmodel

import androidx.lifecycle.*
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return userRepository.getThemeSetting()
    }

    fun saveThemeSetting(isNightMode: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(isNightMode)
        }
    }

    fun getUsername() : LiveData<String> {
        return userRepository.getUsername()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            userRepository.saveUsername(username)
        }
    }
}