package com.mankart.mygithubuser.utils

import android.app.Application
import android.content.Context
import com.mankart.mygithubuser.data.database.FavUserDatabase
import com.mankart.mygithubuser.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context) : UserRepository {
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()
        return UserRepository.getInstance(dao)
    }
}