package com.mankart.mygithubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mankart.mygithubuser.data.datastore.SettingPreference

class ViewModelFactory(private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass ${modelClass.name}")
    }
}