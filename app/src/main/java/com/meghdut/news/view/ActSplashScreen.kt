package com.meghdut.news.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.meghdut.news.R

class ActSplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_splash_screen)

        Handler().postDelayed({
            val li = Intent(this, ActDeshboardScreen::class.java)
            startActivity(li)
            finish()
        }, 2000)
    }
}
