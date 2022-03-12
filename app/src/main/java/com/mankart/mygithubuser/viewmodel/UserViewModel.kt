package com.mankart.mygithubuser.viewmodel

import androidx.lifecycle.*
import com.mankart.mygithubuser.model.RepoModel
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import com.mankart.mygithubuser.services.ApiConfig
import com.mankart.mygithubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<Event<UserModel>>()
    val user: LiveData<Event<UserModel>> = _user

    private val _listUser = MutableLiveData<ArrayList<UserModel>>()
    val listUser: LiveData<ArrayList<UserModel>> = _listUser

    private val _userFollower = MutableLiveData<ArrayList<UserModel>>()
    val userFollower: LiveData<ArrayList<UserModel>> = _userFollower

    private val _userFollowing = MutableLiveData<ArrayList<UserModel>>()
    val userFollowing: LiveData<ArrayList<UserModel>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messageToast = MutableLiveData<String>()
    val messageToast: LiveData<String> = _messageToast

    private val _listRepo = MutableLiveData<Event<ArrayList<RepoModel>>>()
    val listRepo: LiveData<Event<ArrayList<RepoModel>>> = _listRepo

    fun searchUserByQuery(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object:  Callback<UsersListModel> {
            override fun onResponse(
                call: Call<UsersListModel>,
                response: Response<UsersListModel>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()?.items
                    if (responseBody != null && responseBody.size > 0) {
                        _listUser.value = responseBody
                    } else {
                        _messageToast.value = "No users were found matching the query"
                    }
                } else {
                    _messageToast.value = response.message()
                }
            }

            override fun onFailure(call: Call<UsersListModel>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }

        })
    }

    fun clearListUser() {
        _listUser.value?.clear()
    }

    fun getUserByUsername(username: String?) {
        _isLoading.value = true
        val detailUser = username?.let { ApiConfig.getApiService().getUser(it) }
        detailUser?.enqueue(object: Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = Event(responseBody)
                    }
                } else {
                    _messageToast.value = response.message()
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }

        })
    }

    fun getUserFollow(tab: String, username: String?) {
        _isLoading.value = true
        val dataUserFollow = username?.let { ApiConfig.getApiService().getUserFollow(username, tab, 100) }
        dataUserFollow?.enqueue(object : Callback<ArrayList<UserModel>> {
            override fun onResponse(
                call: Call<ArrayList<UserModel>>,
                response: Response<ArrayList<UserModel>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        when (tab) {
                            "followers" -> _userFollower.value = responseBody
                            "following" -> _userFollowing.value = responseBody
                        }
                    }
                } else {
                    _messageToast.value = response.message()
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }
        })
    }

    fun getListUserRepos(username: String?) {
        _isLoading.value = true
        val client = username?.let { ApiConfig.getApiService().getRepos(it) }
        client?.enqueue(object : Callback<ArrayList<RepoModel>> {
            override fun onResponse(
                call: Call<ArrayList<RepoModel>>,
                response: Response<ArrayList<RepoModel>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listRepo.value = Event(responseBody)
                    }
                } else {
                    _messageToast.value = response.message()
                }
            }

            override fun onFailure(call: Call<ArrayList<RepoModel>>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }

        })
    }
}