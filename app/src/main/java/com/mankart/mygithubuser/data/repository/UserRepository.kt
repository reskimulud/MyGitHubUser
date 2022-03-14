package com.mankart.mygithubuser.data.repository

import androidx.lifecycle.LiveData
import com.mankart.mygithubuser.data.database.FavoriteUserDao
import com.mankart.mygithubuser.data.model.UserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(
    private val databaseDao: FavoriteUserDao
) {
    private val executorStatic: ExecutorService = Executors.newSingleThreadExecutor()

    fun getListFavUser() : LiveData<List<UserModel>> {
        return databaseDao.getFavUser()
    }

    fun insertFavUser(user: UserModel) {
        executorStatic.execute {
            user.isFavorite = true
            databaseDao.insertFavUser(user)
        }
    }

    fun deleteFavUser(user: UserModel) {
        executorStatic.execute {
            user.isFavorite = false
            databaseDao.deleteFavUser(user)
        }
    }

    fun isFavouritesUser(id: String) {
        executorStatic.execute { databaseDao.isFavorites(id) }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            databaseDao: FavoriteUserDao
        ) : UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(databaseDao)
            }.also { instance = it }
    }
}