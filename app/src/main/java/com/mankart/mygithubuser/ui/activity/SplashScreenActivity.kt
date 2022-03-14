package com.mankart.mygithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.viewmodel.MainViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val delayMillis = 2000L
        factory = ViewModelFactory.getInstance(this)

        mainViewModel.getThemeSetting().observe(this) { isNightMode: Boolean ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val moveIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }, delayMillis)
    }
}