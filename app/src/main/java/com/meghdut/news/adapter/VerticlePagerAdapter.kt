package com.meghdut.news.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemDeshboardListBinding
import com.meghdut.news.utils.Common

class VerticlePagerAdapter(var activity : Activity) : PagerAdapter() {


    lateinit var itemDeshboardListBinding : ItemDeshboardListBinding
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        itemDeshboardListBinding = DataBindingUtil.inflate(LayoutInflater.from(container.context),
            R.layout.item_deshboard_list,container,false)

        itemDeshboardListBinding.relNewsMedia.layoutParams.height = (Common.getDeviceWidth(activity) * 0.35).toInt()
        container.addView(itemDeshboardListBinding.root)
        return itemDeshboardListBinding.root
    }

    override fun getCount(): Int {
        return 10
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}