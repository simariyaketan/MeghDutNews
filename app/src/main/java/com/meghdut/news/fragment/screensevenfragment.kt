package com.meghdut.news.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.meghdut.news.MyApplication
import com.meghdut.news.R
import com.meghdut.news.adapter.PersonalAppAdapter
import com.meghdut.news.databinding.ScreenSevenFragmentBinding
import com.meghdut.news.model.category.CategoryData
import kotlinx.android.synthetic.main.screen_seven_fragment.*


class screensevenfragment : Fragment(), PersonalAppAdapter.PersonalAppClickListner {

    lateinit var categoryArrayList: ArrayList<CategoryData>
    lateinit var personalAppAdapter: PersonalAppAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenSevenFragmentBinding: ScreenSevenFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.screen_seven_fragment, container, false
        )


        categoryArrayList =
            MyApplication.getInstance()?.categoryArrayList!!

        val layoutManager: FlexboxLayoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW_REVERSE
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY

        screenSevenFragmentBinding.recPersonalApp.layoutManager = layoutManager

        var itemAnimator : SimpleItemAnimator   = screenSevenFragmentBinding.recPersonalApp.getItemAnimator() as SimpleItemAnimator
        itemAnimator.setSupportsChangeAnimations(false)

        personalAppAdapter = PersonalAppAdapter(categoryArrayList!!)


        screenSevenFragmentBinding.recPersonalApp.adapter = personalAppAdapter
        personalAppAdapter.notifyDataSetChanged()
        personalAppAdapter.setPersonalAppClickListner(this)
        return screenSevenFragmentBinding.root
    }

    override fun selecteCategory(position: Int) {
        val categoryData: CategoryData = categoryArrayList.get(position)
        if (categoryData.isSelected)
            categoryData.isSelected = false
        else
            categoryData.isSelected = true
        personalAppAdapter.notifyItemChanged(position)
    }
}