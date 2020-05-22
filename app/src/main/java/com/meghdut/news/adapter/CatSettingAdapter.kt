package com.meghdut.news.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemCatSettingListBinding
import com.meghdut.news.utils.Common

class CatSettingAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemCatSettingListBinding: ItemCatSettingListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_cat_setting_list, parent, false
        )
        val catSettingViewHolder: CatSettingViewHolder =
            CatSettingViewHolder(itemCatSettingListBinding)
        return catSettingViewHolder
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val catSettingViewHolder: CatSettingViewHolder = holder as CatSettingViewHolder
        catSettingViewHolder.itemCatSettingListBinding.relCategoryMain.layoutParams.height =
            (Common.getDeviceWidth(activity) * 0.35).toInt()
    }

    inner class CatSettingViewHolder(var itemCatSettingListBinding: ItemCatSettingListBinding) :
        RecyclerView.ViewHolder(itemCatSettingListBinding.root) {

    }
}