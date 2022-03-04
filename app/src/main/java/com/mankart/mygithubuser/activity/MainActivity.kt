package com.mankart.mygithubuser.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivityMainBinding
import com.mankart.mygithubuser.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        initObserver()
        showRecycleList()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_foreground)
    }

    private fun initObserver() {
        userViewModel.listUser.observe(this) {
            listUserAdapter.setData(it)
        }
        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        userViewModel.messageToast.observe(this) {
            showToast(it)
        }
        userViewModel.user.observe(this) {
            val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
            moveIntent.putExtra(DetailUserActivity.PUT_EXTRA, it)
            startActivity(moveIntent)
        }
    }

    private fun showRecycleList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        listUserAdapter = ListUserAdapter()
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String?) {
                userViewModel.getUserByUsername(username)
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
                userViewModel.searchUserByQuery(query)
                searchView.clearFocus()
                return true
            }
        })

        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String, long: Boolean = true) {
        Toast.makeText(this@MainActivity, message, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}
