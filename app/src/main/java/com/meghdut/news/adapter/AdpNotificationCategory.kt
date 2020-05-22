package com.meghdut.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemNotificationCatListBinding

class AdpNotificationCategory() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemNotificationCatListBinding: ItemNotificationCatListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notification_cat_list,
                parent,
                false
            )
        val notificationCategoryViewHolder: NotificationCategoryViewHolder =
            NotificationCategoryViewHolder(itemNotificationCatListBinding)
        return notificationCategoryViewHolder
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class NotificationCategoryViewHolder(var itemNotificationCatListBinding: ItemNotificationCatListBinding) :
        RecyclerView.ViewHolder(itemNotificationCatListBinding.root) {

    }
}