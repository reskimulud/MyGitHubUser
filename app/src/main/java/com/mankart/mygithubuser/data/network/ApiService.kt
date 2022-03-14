package com.mankart.mygithubuser.data.network

import com.mankart.mygithubuser.BuildConfig
import com.mankart.mygithubuser.data.model.RepoModel
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.model.UsersListModel
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
    ) : Call<List<UserModel>>

    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    @GET("users/{username}/repos")
    fun getRepos(
        @Path("username") username: String
    ) : Call<List<RepoModel>>
}
