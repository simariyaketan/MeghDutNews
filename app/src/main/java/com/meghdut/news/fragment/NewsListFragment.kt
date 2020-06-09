package com.meghdut.news.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemDeshboardListBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.FontSize
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.network.WebAPIClient
import com.meghdut.news.view.ActDeshboardScreen
import com.meghdut.news.view.ActVideoPlayer

class NewsListFragment(var newsData: NewsData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var itemDeshboardListBinding: ItemDeshboardListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container?.context),
            R.layout.item_deshboard_list, container, false
        )

        itemDeshboardListBinding.relNewsMedia.layoutParams.height =
            (Common.getDeviceWidth(activity!!) * 0.50).toInt()

        itemDeshboardListBinding.relNewsShare.setOnClickListener {
            Log.d("click", "click")
            (activity as ActDeshboardScreen).SctollLeft(1)
        }
        Log.d("video_url","video_url = "+newsData.file)
        itemDeshboardListBinding.imgVideoPlayIcon.setOnClickListener {
            Log.d("video_url","video_url = "+newsData.videoPath)
            val vi = Intent(activity!!, ActVideoPlayer::class.java)
            vi.putExtra("video_url",newsData.videoPath)
            startActivity(vi)
        }

        itemDeshboardListBinding.linRight.setOnClickListener {

            val shareData : String = newsData.title+" \n" +
                    "\n" +
                    "વધુ સમાચાર વાંચો :\n" +
                    WebAPIClient.websiteUrl+"newsdetails?newsid="+newsData.newsid+"\n" +
                    "\n" +
                    "વોટ્સએપ ન્યુઝ અને બીજા સમાચારો માટે ડાઉનલોડ કરો "+WebAPIClient.websiteUrl+"home"+" official મોબાઈલ એપ તમારા ios અથવા android મોબાઈલમાં :\n"
                    //"https://goo.gl/TPG5wA"

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareData)
            startActivity(Intent.createChooser(shareIntent,"send to"))
        }

        itemDeshboardListBinding.fontSize = Common.getFontSize(activity!!)

        newsData.footerTitle = newsData.appTitle + " " + "<font color='#EA1F25'>   વધુ વાંચો</font>"
        itemDeshboardListBinding.newsdata = newsData
        itemDeshboardListBinding.nighDayColor = Common.GetNightDayColor(activity!!)
        return itemDeshboardListBinding.root
    }
}