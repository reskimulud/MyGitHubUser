package com.mankart.mygithubuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivityMainBinding
import com.mankart.mygithubuser.model.UsersModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private val list: ArrayList<UsersModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        dataUsers()
        Log.d("MainActivity", list.toString())
        showRecycleList()

        supportActionBar?.hide()
    }

    private fun showRecycleList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersModel) {
                val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                moveIntent.putExtra(DetailUserActivity.PUT_EXTRA, data)
                startActivity(moveIntent)
            }
        })
    }

    private fun dataUsers() {
        list.add(
            UsersModel(
                "reski-mulud-muchamad",
                "Reski Mulud Muchamad",
                R.drawable.user11,
                "Muhammadiyah University of Sukabumi",
                "Kp. Selajambu, RT 04/01, Des. Sasagaran, Kec. Kebonpedes",
                30,
                7,
                40
            )
        )
        list.add(
            UsersModel(
                "sandhikagalih",
                "Sandhika Galih",
                R.drawable.user12,
                "Pasundan University Bandung",
                "Indonesia",
                37,
                5717,
                4
            )
        )
        list.add(
            UsersModel(
                "JakeWharton",
                "Jake Wharton",
                R.drawable.user1,
                "Google, Inc.",
                "Pittsburgh, PA, USA",
                102,
                56995,
                12
            )
        )
        list.add(
            UsersModel(
                "amitshekhariitbhu",
                "Amit Shekar",
                R.drawable.user2,
                "@MindOrksOpenSource",
                "New Delhi, India",
                37,
                5153,
                2
            )
        )
        list.add(
            UsersModel(
                "romainguy",
                "Romain Guy",
                R.drawable.user3,
                "Google",
                "California",
                9,
                7972,
                0
            )
        )
        list.add(
            UsersModel(
                "chrisbanes",
                "Chris Banes",
                R.drawable.user4,
                "@google working on @android",
                "Sydney, Australia",
                30,
                14725,
                1
            )
        )
        list.add(
            UsersModel(
                "tipsy",
                "David",
                R.drawable.user5,
                "Working Group Two",
                "Trondheim, Norway",
                56,
                788,
                0
            )
        )
        list.add(
            UsersModel(
                "ravi8x",
                "Ravi Tamada",
                R.drawable.user6,
                "Android Hive |  Droid5",
                "India",
                28,
                18628,
                3
            )
        )
        list.add(
            UsersModel(
                "jasoet",
                "Deny Prasetyo",
                R.drawable.user7,
                "@gojek-engineering",
                "Kota Gede, Yogyakarta, Indonesia",
                44,
                277,
                39
            )
        )
        list.add(
            UsersModel(
                "budioktaviyan",
                "Budi Oktaviyan",
                R.drawable.user8,
                "@KotlinId",
                "Jakarta, Indonesia",
                110,
                178,
                23
            )
        )
        list.add(
            UsersModel(
                "hendisantika",
                "Hendi Santika",
                R.drawable.user9,
                "@JVMDeveloperID @KotlinId",
                "Bojongsoang - Bandung Jawa Barat",
                1064,
                428,
                61
            )
        )
        list.add(
            UsersModel(
                "sidiqpermana",
                "Sidiq Permana",
                R.drawable.user10,
                "Nusantara Beta Studio",
                "Jakarta, Indonesia",
                65,
                465,
                10
            )
        )
    }
}