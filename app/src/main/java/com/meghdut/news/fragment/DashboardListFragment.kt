package com.meghdut.news.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.adapter.VerticlePagerAdapter
import com.meghdut.news.databinding.FragmentDashboardListBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.news.NewsClass
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.network.WebAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardListFragment() : Fragment() {

    lateinit var verticlePagerAdapter: VerticlePagerAdapter
    var newsArrayList: ArrayList<NewsData> = arrayListOf()

    override fun onStart() {
        super.onStart()
        GetNewsList(0)
    }

    lateinit var fragmentDashboardListBinding: FragmentDashboardListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDashboardListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container?.context),
            R.layout.fragment_dashboard_list,
            container,
            false
        )

        var newsArrayList: ArrayList<NewsData> = ArrayList()

        val verticlePagerAdapter: VerticlePagerAdapter = VerticlePagerAdapter(
            activity!!, newsArrayList, fragmentDashboardListBinding.imgBackIcon,
            activity!!.supportFragmentManager
        )
        fragmentDashboardListBinding.vPagerFavNewsList.adapter = verticlePagerAdapter
        return fragmentDashboardListBinding.root
    }

    fun GetNewsList(offset: Int) {
        Log.d("offset", "offset = " + offset)
        if (offset == 0) {
            newsArrayList.clear()
        }

        WebAPIClient.getInstance(activity)
            .checkInternet()
            ?.getFavoriteNews(10, offset,
                object : Callback<NewsClass> {
                    override fun onFailure(call: Call<NewsClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        // closeLoader()
                    }

                    override fun onResponse(
                        call: Call<NewsClass>,
                        response: Response<NewsClass>
                    ) {
                        // closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {
                                fragmentDashboardListBinding.relNoRecordFound.visibility = View.GONE
                                fragmentDashboardListBinding.vPagerFavNewsList.visibility =
                                    View.VISIBLE
                                newsArrayList.addAll(list)
                                if (offset == 0) {
                                    verticlePagerAdapter = VerticlePagerAdapter(
                                        activity!!,
                                        newsArrayList,
                                        fragmentDashboardListBinding.imgBackIcon,
                                        activity!!.supportFragmentManager
                                    )
                                    fragmentDashboardListBinding.vPagerFavNewsList.adapter =
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
                                        fragmentDashboardListBinding.relNoRecordFound.visibility =
                                            View.VISIBLE
                                        fragmentDashboardListBinding.vPagerFavNewsList.visibility =
                                            View.GONE
                                    }
                                    Common.ShowToast(activity!!, message)
                                } else {
                                    //UnauthoriseUser()
                                }
                            }
                        }

                    }

                })
    }
}