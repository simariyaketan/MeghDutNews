package com.meghdut.news.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemCatSettingListBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.category.CategoryData

class CatSettingAdapter(var activity: Activity, var categoryArrayList: ArrayList<CategoryData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private lateinit var catSettingClickListner: CatSettingClickListner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemCatSettingListBinding: ItemCatSettingListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_cat_setting_list, parent, false
        )
        itemCatSettingListBinding.relCategoryMain.setOnClickListener(this)
        val catSettingViewHolder: CatSettingViewHolder =
            CatSettingViewHolder(itemCatSettingListBinding)
        return catSettingViewHolder
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val categoryData: CategoryData = categoryArrayList.get(position)
        val catSettingViewHolder: CatSettingViewHolder = holder as CatSettingViewHolder
        catSettingViewHolder.itemCatSettingListBinding.relCategoryMain.layoutParams.height =
            (Common.getDeviceWidth(activity) * 0.35).toInt()
        catSettingViewHolder.itemCatSettingListBinding.relCategoryMain.tag = position
        catSettingViewHolder.Bind(categoryData)

        catSettingViewHolder.itemCatSettingListBinding.nighDayColor = Common.GetNightDayColor(activity)
        catSettingViewHolder.itemCatSettingListBinding.fontSize = Common.getFontSize(activity)
    }

    inner class CatSettingViewHolder(var itemCatSettingListBinding: ItemCatSettingListBinding) :
        RecyclerView.ViewHolder(itemCatSettingListBinding.root) {
        fun Bind(categoryData: CategoryData) {
            itemCatSettingListBinding.categoryData = categoryData
        }
    }

    fun setCatSettingClickListner(catSettingClickListner: CatSettingClickListner) {
        this.catSettingClickListner = catSettingClickListner
    }

    interface CatSettingClickListner {
        fun selectFavoriteCategory(position: Int)
    }

    override fun onClick(view: View?) {
        val viewId = view?.id
        val position: Int = view?.tag as Int
        if (viewId == R.id.relCategoryMain) {
            catSettingClickListner.selectFavoriteCategory(position)
        }
    }

}