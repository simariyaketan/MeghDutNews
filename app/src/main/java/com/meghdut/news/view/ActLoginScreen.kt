package com.meghdut.news.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.databinding.ActLoginScreenBinding

class ActLoginScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actLoginScreenBinding: ActLoginScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_login_screen)

        actLoginScreenBinding.lindontHaveAccount.setOnClickListener {
            val li = Intent(this, ActSignUpScreen::class.java)
            startActivity(li)
        }

        actLoginScreenBinding.txtSignIn.setOnClickListener {
            val otpi = Intent(this, ActOtpScreen::class.java)
            startActivity(otpi)
        }

    }
}
