package com.mankart.mygithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import com.mankart.mygithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> = _user

    private val _listUser = MutableLiveData<ArrayList<UserModel>>()
    val listUser: LiveData<ArrayList<UserModel>> = _listUser

    private val _userFollow = MutableLiveData<UsersListModel>()
    val userFollow: LiveData<UsersListModel> = _userFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messageToast = MutableLiveData<String>()
    val messageToast: LiveData<String> = _messageToast

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

    fun searchUserByUsername(username: String?) {
        _isLoading.value = true
        val detailUser = username?.let { ApiConfig.getApiService().getUser(it) }
        detailUser?.enqueue(object: Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    _messageToast.value = response.message()
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _messageToast.value = t.message.toString()
            }

        })
    }
}