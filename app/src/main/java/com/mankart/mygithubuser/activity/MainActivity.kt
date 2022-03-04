package com.mankart.mygithubuser.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivityMainBinding
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.model.UsersListModel
import com.mankart.mygithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private var list: ArrayList<UserModel> = arrayListOf()

    companion object {
        const val TAG = "Main Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        dataUsers()
        Log.e("MainActivity", list.toString())
        showRecycleList()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_foreground)
    }

    private fun showRecycleList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        listUserAdapter = ListUserAdapter()
        rvUser.adapter = listUserAdapter

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
                                val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                                moveIntent.putExtra(DetailUserActivity.PUT_EXTRA, responseBody)
                                startActivity(moveIntent)
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                listUserAdapter.clearData()
                searchUserByQuery(query)
                searchView.clearFocus()
                return true
            }
        })

        return true
    }

    private fun searchUserByQuery(query: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<UsersListModel> {
            override fun onResponse(call: Call<UsersListModel>, response: Response<UsersListModel>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.items
                    if (responseBody != null) {
                        listUserAdapter.setData(responseBody)
                        Log.e(TAG, "ini isi list nih : $list")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersListModel>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
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

    private fun dataUsers() {
        list.add(
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/63949402?v=4",
                0,
                "Reski Mulud Muchamad",
                "UMMI",
                "Sukabumi",
                40,
                "reskimlud"
            )
        )
    }
}
