package com.mankart.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.databinding.ActivitySettingBinding
import com.mankart.mygithubuser.ui.fragment.PreferenceFragment
import com.mankart.mygithubuser.data.viewmodel.MainViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        supportFragmentManager.commit {
            add(binding.settingHolder.id, PreferenceFragment(), PreferenceFragment::class.java.simpleName)
        }

        supportActionBar?.title = "Settings"
    }
}