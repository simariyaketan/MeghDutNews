package com.meghdut.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meghdut.news.R
import com.meghdut.news.adapter.VerticlePagerAdapter
import com.meghdut.news.databinding.FragmentDashboardListBinding

class DashboardListFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentDashboardListBinding : FragmentDashboardListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container?.context), R.layout.fragment_dashboard_list, container,false
        )

        val verticlePagerAdapter : VerticlePagerAdapter = VerticlePagerAdapter(activity!!)
        fragmentDashboardListBinding.vPagerDashboardList.adapter = verticlePagerAdapter

        return fragmentDashboardListBinding.root
    }
}