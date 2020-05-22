package com.meghdut.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meghdut.news.fragment.DashboardListFragment
import com.meghdut.news.fragment.NewsDetailFragment

class DashboardPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return DashboardListFragment()
        }else {
            return NewsDetailFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}