package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.adapter.AdpNewsHorizontalList
import com.meghdut.news.databinding.ActDeshboardScreenBinding
import com.meghdut.news.dialog.SubscribeNewsBottomSheetDialog
import com.meghdut.news.helper.Common
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.model.news.NewsClass
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.network.WebAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActDeshboardScreen : ActBase() {

    lateinit var newsArrayList: ArrayList<NewsData>
    lateinit var adpNewsHorizontalList: AdpNewsHorizontalList
    lateinit var actDeshboardScreenBinding: ActDeshboardScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        actDeshboardScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_deshboard_screen)

        actDeshboardScreenBinding.imgSetting.setOnClickListener {
            val si = Intent(activity, ActSettingScreen::class.java)
            startActivityForResult(si, 0)
        }
        newsArrayList = ArrayList()
        GetNewsList(0)
    }

    fun SctollLeft(position: Int) {
        Log.d("position", "position = " + position)
        actDeshboardScreenBinding.recDashboard.scrollToPosition(position)
    }

    /*Open Subscription dialog*/
    fun SubscribeNewsDialog() {
        val subscribeNewsBottomSheetDialog: SubscribeNewsBottomSheetDialog =
            SubscribeNewsBottomSheetDialog()

        subscribeNewsBottomSheetDialog.show(supportFragmentManager, "subscribe news")
    }

    /*get new list*/
    fun GetNewsList(offset: Int) {
        Log.d("offset", "offset = " + offset)
        if (offset == 0) {
            showLoader()
            newsArrayList.clear()
        }

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.getNews(10, offset,
                object : Callback<NewsClass> {
                    override fun onFailure(call: Call<NewsClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<NewsClass>,
                        response: Response<NewsClass>
                    ) {
                        closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {
                                actDeshboardScreenBinding.relNoRecordFound.visibility = View.GONE
                                actDeshboardScreenBinding.recDashboard.visibility = View.VISIBLE
                                newsArrayList.addAll(list)
                                if (offset == 0) {
                                    adpNewsHorizontalList = AdpNewsHorizontalList(
                                        activity!!,
                                        newsArrayList,
                                        getSupportManager()
                                    )
                                    actDeshboardScreenBinding.recDashboard.adapter =
                                        adpNewsHorizontalList
                                } else {
                                    adpNewsHorizontalList.verticlePagerAdapter.PagerNotify(
                                        newsArrayList
                                    )
                                }
                            } ?: let {
                                if(code == 0) {
                                    if (offset == 0) {
                                        actDeshboardScreenBinding.relNoRecordFound.visibility =
                                            View.VISIBLE
                                        actDeshboardScreenBinding.recDashboard.visibility =
                                            View.GONE
                                    }
                                    Common.ShowToast(activity!!, message)
                                }else{
                                    UnauthoriseUser()
                                }
                            }


                        }

                    }

                })
    }

    /*SubScribe News*/
    fun SubscribeNews(name: String, email: String) {
        showLoader()
        WebAPIClient.getInstance(activity)
            .checkInternet()
            ?.SubscribeNews(name, email,
                object : Callback<CommonClass> {
                    override fun onFailure(call: Call<CommonClass>, t: Throwable) {
                        closeLoader()
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                    }

                    override fun onResponse(
                        call: Call<CommonClass>,
                        response: Response<CommonClass>
                    ) {
                        response.body()?.apply {
                            closeLoader()
                            Common.ShowToast(activity!!, message)
                        }
                    }
                })
    }

    /*Add To Favorite News*/
    fun AddToFAvoriteNews(newsId : String){
        showLoader()
        WebAPIClient.getInstance(activity)
            .checkInternet()
            ?.AddToFavoriteNews(newsId,
                object : Callback<CommonClass> {
                    override fun onFailure(call: Call<CommonClass>, t: Throwable) {
                        closeLoader()
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                    }

                    override fun onResponse(
                        call: Call<CommonClass>,
                        response: Response<CommonClass>
                    ) {
                        response.body()?.apply {
                            closeLoader()
                            Common.ShowToast(activity!!, message)
                        }
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("requestCode", "requestCode = " + requestCode + "==" + resultCode)
        if (requestCode == 0) {
            GetNewsList(0)
        }
    }
}
