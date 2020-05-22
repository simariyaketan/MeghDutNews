package com.meghdut.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemPersonalAppListBinding

class PersonalAppAdapter(var personalAppArray: Array<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemPersonalAppListBinding: ItemPersonalAppListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_personal_app_list, parent, false
        )
        val personalAppViewHolder: PersonalAppViewHolder =
            PersonalAppViewHolder(itemPersonalAppListBinding)
        return personalAppViewHolder
    }

    override fun getItemCount(): Int {
        return personalAppArray.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val personalAppViewHolder: PersonalAppViewHolder = holder as PersonalAppViewHolder
        personalAppViewHolder.itemPersonalAppListBinding.txtPersonalAppName.setText(
            personalAppArray.get(
                position
            )
        )
    }

    class PersonalAppViewHolder(var itemPersonalAppListBinding: ItemPersonalAppListBinding) :
        RecyclerView.ViewHolder(itemPersonalAppListBinding.root) {

    }
}