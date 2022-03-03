package com.mankart.mygithubuser.services

import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ) : Call<UserModel>
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ) : Call<UsersListModel>
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ) : Call<UserModel>
    @GET("users/{username}/following")
    fun getUserUserFllowing(
        @Path("username") username: String
    ) : Call<UserModel>
}
