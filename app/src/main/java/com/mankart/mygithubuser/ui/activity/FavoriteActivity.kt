package com.mankart.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.databinding.ActivityFavoriteBinding
import com.mankart.mygithubuser.ui.adapter.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        showRecycleView()

        supportActionBar?.title = "Favourites"
    }

    private fun showRecycleView() {
        rvUser.layoutManager = LinearLayoutManager(this)
        listUserAdapter = ListUserAdapter { user ->
            if (user.isFavorite) {
                Log.e("FAV", "Set to Fav")
            } else {
                Log.e("FAV", "Set to No Fav")
            }
        }
        rvUser.adapter = listUserAdapter
    }
}