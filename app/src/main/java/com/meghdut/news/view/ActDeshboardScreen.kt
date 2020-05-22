package com.meghdut.news.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.adapter.DashboardPageAdapter
import com.meghdut.news.databinding.ActDeshboardScreenBinding

class ActDeshboardScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actDeshboardScreenBinding: ActDeshboardScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_deshboard_screen)

        actDeshboardScreenBinding.imgSetting.setOnClickListener {
            val si = Intent(this, ActSettingScreen::class.java)
            startActivity(si)
        }

        val dashboardPageAdapter: DashboardPageAdapter =
            DashboardPageAdapter(supportFragmentManager)
        actDeshboardScreenBinding.viewPagerDashboard.adapter = dashboardPageAdapter
    }
}
