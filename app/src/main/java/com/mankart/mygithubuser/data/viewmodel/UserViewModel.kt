package com.mankart.mygithubuser.data.viewmodel

import androidx.lifecycle.*
import com.mankart.mygithubuser.data.model.RepoModel
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.model.UsersListModel
import com.mankart.mygithubuser.data.network.ApiConfig
import com.mankart.mygithubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<Event<UserModel>>()
    val user: LiveData<Event<UserModel>> = _user

    private val _listUser = MutableLiveData<List<UserModel>>()
    val listUser: LiveData<List<UserModel>> = _listUser

    private val _userFollower = MutableLiveData<List<UserModel>>()
    val userFollower: LiveData<List<UserModel>> = _userFollower

    private val _userFollowing = MutableLiveData<List<UserModel>>()
    val userFollowing: LiveData<List<UserModel>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messageToast = MutableLiveData<String>()
    val messageToast: LiveData<String> = _messageToast

    private val _listRepo = MutableLiveData<Event<List<RepoModel>>>()
    val listRepo: LiveData<Event<List<RepoModel>>> = _listRepo

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
        dataUserFollow?.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
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

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }
        })
    }

    fun getListUserRepos(username: String?) {
        _isLoading.value = true
        val client = username?.let { ApiConfig.getApiService().getRepos(it) }
        client?.enqueue(object : Callback<List<RepoModel>> {
            override fun onResponse(
                call: Call<List<RepoModel>>,
                response: Response<List<RepoModel>>
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

            override fun onFailure(call: Call<List<RepoModel>>, t: Throwable) {
                _isLoading.value = false
                _messageToast.value = t.message.toString()
            }

        })
    }
}