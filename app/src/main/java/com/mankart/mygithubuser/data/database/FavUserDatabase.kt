package com.mankart.mygithubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mankart.mygithubuser.data.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavUserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context) : FavUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDatabase::class.java, "favorites_user_db"
                ).build()
            }
    }
}