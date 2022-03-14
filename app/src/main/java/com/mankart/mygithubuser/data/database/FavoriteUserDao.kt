package com.mankart.mygithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mankart.mygithubuser.data.model.UserModel

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(user: UserModel)

    @Delete
    fun deleteFavUser(user: UserModel)

    @Query("SELECT * FROM fav_user ORDER BY created_at DESC")
    fun getFavUser() : LiveData<List<UserModel>>

    @Query("SELECT EXISTS(SELECT * FROM fav_user WHERE id = :id)")
    fun isFavorites(id: String) : Boolean
}