package com.mankart.mygithubuser.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mankart.mygithubuser.utils.Injection
import com.mankart.mygithubuser.data.repository.UserRepository

class ViewModelFactory private constructor (private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(userRepository) as T
            modelClass.isAssignableFrom(FavUserViewModel::class.java) -> FavUserViewModel(userRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModelClass ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}