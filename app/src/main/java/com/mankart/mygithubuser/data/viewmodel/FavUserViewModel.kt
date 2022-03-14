package com.mankart.mygithubuser.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.repository.UserRepository

class FavUserViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getListFavUser() : LiveData<List<UserModel>> = userRepository.getListFavUser()

    fun insertFavUser(user: UserModel) {
        userRepository.insertFavUser(user)
    }

    fun deleteFavUser(user: UserModel) {
        userRepository.deleteFavUser(user)
    }
}