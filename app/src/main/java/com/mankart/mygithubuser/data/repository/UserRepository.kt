package com.mankart.mygithubuser.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mankart.mygithubuser.data.database.FavoriteUserDao
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.model.RepoModel
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.model.UsersListModel
import com.mankart.mygithubuser.data.network.ApiService
import com.mankart.mygithubuser.utils.AppExecutors
import com.mankart.mygithubuser.utils.DateUtils
import kotlinx.coroutines.coroutineScope
import retrofit2.Call

class UserRepository(
    private val databaseDao: FavoriteUserDao,
    private val pref: SettingPreference,
    private val apiService: ApiService,
    val appExecutors: AppExecutors
) {
    /*
    * Access data from Database (Room)
    * */

    fun getListFavUser() : LiveData<List<UserModel>> {
        return databaseDao.getFavUser()
    }

    fun insertFavUser(user: UserModel) {
        appExecutors.diskIO.execute {
            user.isFavorite = true
            user.createdAt = DateUtils.getCurrentDate()
            databaseDao.insertFavUser(user)
        }
    }

    fun deleteFavUser(user: UserModel) {
        appExecutors.diskIO.execute {
            user.isFavorite = false
            databaseDao.deleteFavUser(user)
        }
    }

    fun isFavouritesUser(id: Int): Boolean {
        return databaseDao.isFavorites(id)
    }


    /*
    * Access data from DataStore (SettingPreference)
    * */

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isNightMode: Boolean) {
        coroutineScope {
            pref.saveThemeSetting(isNightMode)
        }
    }

    fun getUsername() : LiveData<String> {
        return pref.getUsername().asLiveData()
    }

    suspend fun saveUsername(username: String) {
        coroutineScope {
            pref.saveUsername(username)
        }
    }


    /*
    * Access data from API/Network (Retrofit)
    * */

    fun searchUserByQuery(query: String): Call<UsersListModel> {
        return apiService.searchUser(query)
    }

    fun getUserByUsername(username: String): Call<UserModel> {
        return apiService.getUser(username)
    }

    fun getUserFollow(username: String, tab: String): Call<List<UserModel>> {
        return apiService.getUserFollow(username, tab, 100)
    }

    fun getListUserRepos(username: String): Call<List<RepoModel>> {
        return apiService.getRepos(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            databaseDao: FavoriteUserDao,
            pref: SettingPreference,
            apiService: ApiService,
            appExecutors: AppExecutors
        ) : UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(databaseDao, pref, apiService, appExecutors)
            }.also { instance = it }
    }
}