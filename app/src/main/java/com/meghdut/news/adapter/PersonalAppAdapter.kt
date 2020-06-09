package com.meghdut.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemPersonalAppListBinding
import com.meghdut.news.model.category.CategoryData

class PersonalAppAdapter(var categoryArrayList: ArrayList<CategoryData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private lateinit var personalAppClickListner: PersonalAppClickListner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemPersonalAppListBinding: ItemPersonalAppListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_personal_app_list, parent, false
        )

        val personalAppViewHolder: PersonalAppViewHolder =
            PersonalAppViewHolder(itemPersonalAppListBinding)
        personalAppViewHolder.itemPersonalAppListBinding.txtPersonalAppName.setOnClickListener(this)
        return personalAppViewHolder
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val CategoryData: CategoryData = categoryArrayList.get(position)
        val personalAppViewHolder: PersonalAppViewHolder = holder as PersonalAppViewHolder
        personalAppViewHolder.itemPersonalAppListBinding.txtPersonalAppName.tag = position
        personalAppViewHolder.Bind(CategoryData)
    }

    class PersonalAppViewHolder(var itemPersonalAppListBinding: ItemPersonalAppListBinding) :
        RecyclerView.ViewHolder(itemPersonalAppListBinding.root) {
        fun Bind(categoryData: CategoryData) {
            itemPersonalAppListBinding.categorydata = categoryData
        }
    }

    fun setPersonalAppClickListner(personalAppClickListner: PersonalAppClickListner) {
        this.personalAppClickListner = personalAppClickListner
    }

    interface PersonalAppClickListner {
        fun selecteCategory(position: Int)
    }

    override fun onClick(view: View?) {
        val viewId: Int? = view?.id
        val position: Int = view?.tag as Int

        if (viewId == R.id.txtPersonalAppName) {
            personalAppClickListner.selecteCategory(position)
        }
    }

}