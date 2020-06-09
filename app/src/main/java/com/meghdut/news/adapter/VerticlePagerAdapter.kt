package com.meghdut.news.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.meghdut.news.fragment.NewsListFragment
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.view.ActDeshboardScreen

class VerticlePagerAdapter(
    var activity: Activity,
    var newsArrayList: ArrayList<NewsData>,
    var view: View,
    fm: FragmentManager
) :
    FragmentStatePagerAdapter(fm) {


    /*lateinit var itemDeshboardListBinding: ItemDeshboardListBinding
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val newsData: NewsData = newsArrayList.get(position)
        itemDeshboardListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.item_deshboard_list, container, false
        )

        itemDeshboardListBinding.relNewsMedia.layoutParams.height =
            (Common.getDeviceWidth(activity) * 0.35).toInt()

        itemDeshboardListBinding.newsdata = newsData
        container.addView(itemDeshboardListBinding.root)
        return itemDeshboardListBinding.root
    }*/

    override fun getItem(position: Int): Fragment {
        val newsData: NewsData = newsArrayList.get(position)
        if (activity is ActDeshboardScreen) {
            if (newsData.favourite.equals("yes"))
                view.visibility = View.GONE
            else
                view.visibility = View.VISIBLE
        }
        return NewsListFragment(newsData)
    }

    override fun getCount(): Int {
        return newsArrayList.size
    }

    /*override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }*/

    fun PagerNotify(newArray: ArrayList<NewsData>) {
        Log.d("newsArrayList", "newsArrayList " + newsArrayList.size)
        newsArrayList.addAll(newArray)
        Log.d("newsArrayList", "newsArrayList " + newsArrayList.size)
        notifyDataSetChanged()
    }
}