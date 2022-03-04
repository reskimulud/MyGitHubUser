package com.mankart.mygithubuser.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.activity.DetailUserActivity
import com.mankart.mygithubuser.activity.MainActivity
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.databinding.FragmentFollowerBinding
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import com.mankart.mygithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var listUserAdapter: ListUserAdapter

    companion object {
        val TABS = listOf<String>("followers", "following")
        const val ARG_SECTION_NUMBER ="tab_number"
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tab = TABS[arguments?.getInt(ARG_SECTION_NUMBER, 0)!!.toInt()]
        val username = arguments?.getString(USERNAME)

        getUserFollow(tab, username)
        showRecycleList()
    }

    private fun getUserFollow(tab: String, username: String?) {
        showLoading(true)
        val dataUserFollow = username?.let { ApiConfig.getApiService().getUserFollow(it, tab) }
        dataUserFollow?.enqueue(object : Callback<ArrayList<UserModel>> {
            override fun onResponse(
                call: Call<ArrayList<UserModel>>,
                response: Response<ArrayList<UserModel>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listUserAdapter.setData(responseBody)
                    }
                } else {
                    Log.e(tab, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                showLoading(false)
                Log.e(tab, "onFailure : ${t.message}")
            }

        })
    }

    private fun showRecycleList() {
        binding.rvFollow.layoutManager = LinearLayoutManager(context)
        listUserAdapter = ListUserAdapter()
        binding.rvFollow.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String?) {
                showLoading(true)
                val detailUser = username?.let { ApiConfig.getApiService().getUser(it) }
                detailUser?.enqueue(object : Callback<UserModel> {
                    override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                val moveIntent = Intent(activity, DetailUserActivity::class.java)
                                moveIntent.putExtra(DetailUserActivity.PUT_EXTRA, responseBody)
                                startActivity(moveIntent)
                            }
                        } else {
                            showToast(response.message())
                            Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        showToast(t.message.toString())
                        Log.e(MainActivity.TAG, "onFailure: ${t.message}")
                    }

                })
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String, long: Boolean = true) {
        Toast.makeText(context, message, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}