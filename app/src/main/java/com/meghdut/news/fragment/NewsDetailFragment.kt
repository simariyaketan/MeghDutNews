package com.meghdut.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meghdut.news.R
import com.meghdut.news.databinding.FragmentNewsDetailBinding

class NewsDetailFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentNewsDetailBinding : FragmentNewsDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container?.context), R.layout.fragment_news_detail, container,false
        )

        return fragmentNewsDetailBinding.root
    }
}