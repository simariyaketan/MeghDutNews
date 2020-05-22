package com.meghdut.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.adapter.AdpNotificationCategory
import com.meghdut.news.adapter.CatSettingAdapter
import com.meghdut.news.databinding.ActSettingScreenBinding

class ActSettingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actSettingScreenBinding: ActSettingScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_setting_screen)

        actSettingScreenBinding.imgBack.setOnClickListener {
            finish()
        }

        val catSettingAdapter: CatSettingAdapter = CatSettingAdapter(this)
        actSettingScreenBinding.recCategorySetting.adapter = catSettingAdapter

        val adpNotificationCategory: AdpNotificationCategory = AdpNotificationCategory()
        actSettingScreenBinding.recNotificationCat.adapter = adpNotificationCategory
    }
}
