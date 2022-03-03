package com.mankart.mygithubuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mankart.mygithubuser.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val moveIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }, 2000)
    }
}