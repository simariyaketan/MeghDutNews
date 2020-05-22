package com.meghdut.news.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.meghdut.news.R
import com.meghdut.news.adapter.AppDetailAdapter
import kotlinx.android.synthetic.main.act_app_detials.*

class ActAppDetials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_app_detials)

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        val appDetailAdapter: AppDetailAdapter = AppDetailAdapter(supportFragmentManager)
        viewPagerAppDetail.adapter = appDetailAdapter

        txtSkip.setOnClickListener {
            GoToNextScreen()
        }

        txtNext.setOnClickListener {
            GoToNextScreen()
        }
    }

    private fun GoToNextScreen() {
        Log.d("viewPagerAppDetail", "viewPagerAppDetail = " + viewPagerAppDetail.currentItem)
        if (viewPagerAppDetail.currentItem == 6) {
            val di = Intent(this, ActDeshboardScreen::class.java)
            startActivity(di)
            finish()
        } else {
            val nextScreen: Int = viewPagerAppDetail.currentItem + 1
            viewPagerAppDetail.currentItem = nextScreen
        }
    }
}
