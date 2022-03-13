package com.mankart.mygithubuser.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mankart.mygithubuser.ui.adapter.ListUserAdapter
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivityMainBinding
import com.mankart.mygithubuser.data.viewmodel.UserViewModel
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.ui.fragment.HomeFragment
import com.mankart.mygithubuser.ui.fragment.SearchFragment
import com.mankart.mygithubuser.data.viewmodel.MainViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        listUserAdapter = ListUserAdapter()

        homeFragment()
        initObserve()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.logo)
    }

    private fun initObserve() {
        userViewModel.listUser.observe(this) {
            if (it != null && it.size > 0) {
                supportFragmentManager.commit {
                    replace(binding.fragmentPlaceholder.id, SearchFragment(), SearchFragment::class.java.simpleName)
                    addToBackStack(null)
                }
            }
        }
    }

    private fun homeFragment() {
        supportFragmentManager.commit {
            replace(binding.fragmentPlaceholder.id, HomeFragment(), HomeFragment::class.java.simpleName)
            addToBackStack(null)
        }
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
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}
