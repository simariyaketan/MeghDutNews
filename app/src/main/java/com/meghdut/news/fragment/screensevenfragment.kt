package com.meghdut.news.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.meghdut.news.R
import com.meghdut.news.adapter.PersonalAppAdapter
import com.meghdut.news.databinding.ScreenSevenFragmentBinding
import kotlinx.android.synthetic.main.screen_seven_fragment.*


class screensevenfragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenSevenFragmentBinding: ScreenSevenFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.screen_seven_fragment, container, false
        )

        val personalAppArray: Array<String> = arrayOf<String>(
            "Abu",
            "Praveen",
            "Sathya",
            "Yogesh",
            "Ram",
            "Abu",
            "Praveen",
            "Sathya",
            "Yogesh",
            "Ram",
            "Abu",
            "Praveen",
            "Sathya",
            "Yogesh",
            "Ram",
            "Abu",
            "Praveen",
            "Sathya",
            "Yogesh",
            "Ram"
        )
        val layoutManager: FlexboxLayoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW_REVERSE
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY
        screenSevenFragmentBinding.recPersonalApp.layoutManager = layoutManager

        val personalAppAdapter: PersonalAppAdapter = PersonalAppAdapter(personalAppArray)
        screenSevenFragmentBinding.recPersonalApp.adapter = personalAppAdapter
        personalAppAdapter.notifyDataSetChanged()
        return screenSevenFragmentBinding.root
    }
}