package com.meghdut.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.databinding.ActSignUpScreenBinding

class ActSignUpScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actSignUpScreenBinding: ActSignUpScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_sign_up_screen)

        actSignUpScreenBinding.linFooter.setOnClickListener {
            finish()
        }
    }
}
