package com.meghdut.news.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemNotificationCatListBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.category.CategoryData

class AdpNotificationCategory(
    var activity: Activity,
    var notificationList: ArrayList<CategoryData>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private lateinit var notificationCategoryClickListner: NotificationCategoryClickListner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemNotificationCatListBinding: ItemNotificationCatListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notification_cat_list,
                parent,
                false
            )
        itemNotificationCatListBinding.linNotificationMain.setOnClickListener(this)
        val notificationCategoryViewHolder: NotificationCategoryViewHolder =
            NotificationCategoryViewHolder(itemNotificationCatListBinding)
        return notificationCategoryViewHolder
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notificationCategoryViewHolder: NotificationCategoryViewHolder =
            holder as NotificationCategoryViewHolder
        notificationCategoryViewHolder.itemNotificationCatListBinding.linNotificationMain.tag =
            position
        val categoryData: CategoryData = notificationList.get(position)
        notificationCategoryViewHolder.Bind(categoryData)
        notificationCategoryViewHolder.itemNotificationCatListBinding.fontSize =
            Common.getFontSize(activity)
        notificationCategoryViewHolder.itemNotificationCatListBinding.nighDayColor =
            Common.GetNightDayColor(activity)
    }

    inner class NotificationCategoryViewHolder(var itemNotificationCatListBinding: ItemNotificationCatListBinding) :
        RecyclerView.ViewHolder(itemNotificationCatListBinding.root) {
        fun Bind(categoryData: CategoryData) {
            itemNotificationCatListBinding.notificationData = categoryData
        }
    }

    fun setNotificationCategoryClickListner(notificationCategoryClickListner: NotificationCategoryClickListner) {
        this.notificationCategoryClickListner = notificationCategoryClickListner
    }

    interface NotificationCategoryClickListner {
        fun SelectNotification(position: Int)
    }

    override fun onClick(view: View?) {
        val viewId: Int? = view?.id
        val position: Int = view?.tag as Int
        if (viewId == R.id.linNotificationMain) {
            notificationCategoryClickListner.SelectNotification(position)
        }
    }

}