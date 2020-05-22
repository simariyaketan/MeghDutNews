package com.meghdut.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.meghdut.news.fragment.*

class AppDetailAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return screenonefragment()
        else if (position == 1)
            return screentwofragment()
        else if (position == 2)
            return screenthreefragment()
        else if (position == 3)
            return screenfourfragment()
        else if (position == 4)
            return screenfivefragment()
        else if (position == 5)
            return screensixfragment()
        else if (position == 6)
            return screensevenfragment()
        return screenonefragment()
    }

    override fun getCount(): Int {
        return 7
    }
}