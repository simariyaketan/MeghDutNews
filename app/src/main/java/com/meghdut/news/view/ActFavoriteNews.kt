package com.meghdut.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.adapter.DashboardPageAdapter
import com.meghdut.news.adapter.VerticlePagerAdapter
import com.meghdut.news.databinding.ActFavoriteNewsBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.news.NewsClass
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.network.WebAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActFavoriteNews : ActBase() {

    lateinit var verticlePagerAdapter: VerticlePagerAdapter

    var newsArrayList: ArrayList<NewsData> = arrayListOf()
    lateinit var actFavoriteNewsBinding: ActFavoriteNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actFavoriteNewsBinding =
            DataBindingUtil.setContentView(this, R.layout.act_favorite_news)

        actFavoriteNewsBinding.nighDayColor = Common.GetNightDayColor(activity!!)
        actFavoriteNewsBinding.imgBackIcon.setOnClickListener {
            finish()
        }

        val dashboardPageAdapter : DashboardPageAdapter = DashboardPageAdapter(supportFragmentManager)
        actFavoriteNewsBinding.vipFavoriteNews.adapter = dashboardPageAdapter


        //GetNewsList(0)
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
            ?.getFavoriteNews(10, offset,
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
                                actFavoriteNewsBinding.relNoRecordFound.visibility = View.GONE
                                actFavoriteNewsBinding.vPagerFavNewsList.visibility = View.VISIBLE
                                newsArrayList.addAll(list)
                                if (offset == 0) {
                                    verticlePagerAdapter = VerticlePagerAdapter(
                                        activity!!,
                                        newsArrayList,
                                        actFavoriteNewsBinding.imgBackIcon,
                                        supportFragmentManager
                                    )
                                    actFavoriteNewsBinding.vPagerFavNewsList.adapter =
                                        verticlePagerAdapter
                                }
                                /*else {
                                    adpNewsHorizontalList.verticlePagerAdapter.PagerNotify(
                                        newsArrayList
                                    )
                                }*/
                            } ?: let {
                                if (code == 0) {
                                    if (offset == 0) {
                                        actFavoriteNewsBinding.relNoRecordFound.visibility =
                                            View.VISIBLE
                                        actFavoriteNewsBinding.vPagerFavNewsList.visibility =
                                            View.GONE
                                    }
                                    Common.ShowToast(activity!!, message)
                                } else {
                                    UnauthoriseUser()
                                }
                            }


                        }

                    }

                })
    }
}
