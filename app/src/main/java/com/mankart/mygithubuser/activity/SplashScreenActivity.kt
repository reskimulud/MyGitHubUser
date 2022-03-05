package com.mankart.mygithubuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.mankart.mygithubuser.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val delayMillis = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            val moveIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }, delayMillis)
    }
}