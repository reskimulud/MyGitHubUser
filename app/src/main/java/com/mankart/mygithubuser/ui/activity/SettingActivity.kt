package com.mankart.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.databinding.ActivitySettingBinding
import com.mankart.mygithubuser.ui.fragment.PreferenceFragment

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            add(binding.settingHolder.id, PreferenceFragment(), PreferenceFragment::class.java.simpleName)
        }

        supportActionBar?.title = "Settings"
    }
}