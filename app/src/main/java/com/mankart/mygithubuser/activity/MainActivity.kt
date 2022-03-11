package com.mankart.mygithubuser.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivityMainBinding
import com.mankart.mygithubuser.fragment.PreferenceFragment
import com.mankart.mygithubuser.viewmodel.UserViewModel
import androidx.fragment.app.commit
import com.mankart.mygithubuser.fragment.HomeFragment
import com.mankart.mygithubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private val userViewModel: UserViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listUserAdapter = ListUserAdapter()

        mainViewModel.changeActionBarTitle(getString(R.string.app_name))

        supportFragmentManager.commit {
            add(binding.fragmentPlaceholder.id, HomeFragment(), HomeFragment::class.java.simpleName)
        }

        mainViewModel.actionBarTitle.observe(this) {
            supportActionBar?.title = it
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_foreground)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                supportFragmentManager.commit {
                    replace(binding.fragmentPlaceholder.id, PreferenceFragment(), PreferenceFragment::class.java.simpleName)
                    addToBackStack(null)
                }
                true
            }
            else -> true
        }
    }
}
