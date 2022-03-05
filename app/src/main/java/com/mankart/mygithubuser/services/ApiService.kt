package com.mankart.mygithubuser.services

import com.mankart.mygithubuser.BuildConfig
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ) : Call<UserModel>

    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ) : Call<UsersListModel>

    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("users/{username}/{follow}")
    fun getUserFollow(
        @Path("username") username: String,
        @Path("follow") follow: String,
        @Query("per_page") perPage: Int
    ) : Call<ArrayList<UserModel>>
}
