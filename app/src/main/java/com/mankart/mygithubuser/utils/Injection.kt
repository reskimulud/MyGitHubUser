package com.mankart.mygithubuser.utils

import android.content.Context
import com.mankart.mygithubuser.data.database.FavUserDatabase
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.network.ApiConfig
import com.mankart.mygithubuser.data.repository.UserRepository
import com.mankart.mygithubuser.ui.activity.dataStore

object Injection {
    fun provideUserRepository(context: Context) : UserRepository {
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()

        val pref = SettingPreference.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService()

        val appExecutors = AppExecutors()
        return UserRepository.getInstance(dao, pref, apiService, appExecutors)
    }
}