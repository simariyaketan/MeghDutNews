package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.databinding.ActNewsDetailBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.news.NewsDetailClass
import com.meghdut.news.network.WebAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActNewsDetail : ActBase() {

    lateinit var actNewsDetailBinding: ActNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actNewsDetailBinding = DataBindingUtil.setContentView(this, R.layout.act_news_detail)

        actNewsDetailBinding.fontSize = Common.getFontSize(activity!!)
        actNewsDetailBinding.nighDayColor = Common.GetNightDayColor(activity!!)
        actNewsDetailBinding.imgBackIcon.setOnClickListener {
            val di : Intent = Intent(activity!!, ActDeshboardScreen::class.java)
            di.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(di)
            finish()
        }
        val newsId: String = intent.getStringExtra("news_id")
        GetNotificationDetail(newsId)
    }

    /*get notification detail*/
    private fun GetNotificationDetail(newsId: String) {
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.GetNesDetail(newsId,
                object : Callback<NewsDetailClass> {
                    override fun onFailure(call: Call<NewsDetailClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<NewsDetailClass>,
                        response: Response<NewsDetailClass>
                    ) {
                        closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {
                                actNewsDetailBinding.newsDetail = newsData
                            } ?: Common.ShowToast(activity!!, message)
                        }

                    }

                })
    }
}
