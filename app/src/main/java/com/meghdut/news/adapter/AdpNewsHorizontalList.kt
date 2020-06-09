package com.meghdut.news.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.meghdut.news.R
import com.meghdut.news.databinding.ItemNewHorizontalListBinding
import com.meghdut.news.databinding.ItemNewsDetailsBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.model.news.NewsData
import com.meghdut.news.network.WebAPIClient
import com.meghdut.news.view.ActDeshboardScreen
import com.meghdut.news.view.ActSettingScreen
import com.meghdut.news.view.ActVideoPlayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdpNewsHorizontalList(
    var activity: Activity,
    var newsArrayList: ArrayList<NewsData>,
    var fm: FragmentManager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var NEW_LIST: Int = 1
    var NEW_DETAILS: Int = 2
    var currentNews: Int = 0

    lateinit var verticlePagerAdapter: VerticlePagerAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == NEW_LIST) {
            val itemNewHorizontalListBinding: ItemNewHorizontalListBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_new_horizontal_list,
                    parent,
                    false
                )
            val newsHorizontalViewHolder: NewsHorizontalViewHolder =
                NewsHorizontalViewHolder(itemNewHorizontalListBinding)


            return newsHorizontalViewHolder
        } else {
            var itemNewsDetailBinding: ItemNewsDetailsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_news_details, parent, false
            )


            val newsDetailsViewHolder: NewsDetailsViewHolder =
                NewsDetailsViewHolder(itemNewsDetailBinding!!)
            return newsDetailsViewHolder
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return NEW_LIST
        } else {
            return NEW_DETAILS
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is NewsHorizontalViewHolder) {

            val newsHorizontalViewHolder: NewsHorizontalViewHolder =
                holder as NewsHorizontalViewHolder

            verticlePagerAdapter = VerticlePagerAdapter(activity!!, newsArrayList,newsHorizontalViewHolder.itemNewHorizontalListBinding.imgAddToFavorite, fm)
            newsHorizontalViewHolder.itemNewHorizontalListBinding.vPagerDashboardList.adapter =
                verticlePagerAdapter

            newsHorizontalViewHolder.itemNewHorizontalListBinding.vPagerDashboardList.addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        currentNews = position

                        Log.d("newsArrayList", "newsArrayList load" + position)
                        if (newsArrayList.size >= 10 && position >= newsArrayList.size - 1) {
                            Log.d("newsArrayList", "newsArrayList load" + position)
                            (activity as ActDeshboardScreen).GetNewsList(position + 1)
                        }
                    }

                })

            newsHorizontalViewHolder.itemNewHorizontalListBinding.imgSetting.setOnClickListener {
                val si = Intent(activity, ActSettingScreen::class.java)
                activity.startActivityForResult(si, 0)
            }

            newsHorizontalViewHolder.itemNewHorizontalListBinding.imgAddToFavorite.setOnClickListener {
                newsHorizontalViewHolder.itemNewHorizontalListBinding.imgAddToFavorite.visibility =
                    View.GONE
                val position: Int =
                    newsHorizontalViewHolder.itemNewHorizontalListBinding.vPagerDashboardList.currentItem
                val newsData: NewsData = newsArrayList.get(position)
                (activity as ActDeshboardScreen).AddToFAvoriteNews(newsData.id.toString())
            }

            newsHorizontalViewHolder.itemNewHorizontalListBinding.nighDayColor =
                Common.GetNightDayColor(activity)

        } else if (holder is NewsDetailsViewHolder) {

            var newsDetailsViewHolder: NewsDetailsViewHolder = holder
            holder.setIsRecyclable(false)
            val newsData: NewsData = newsArrayList.get(currentNews)
            newsDetailsViewHolder.itemNewsDetailBinding.newsDetail = newsData
            if (newsData.viewed.equals("no"))
                MostViewNews(newsData.id.toString())
            newsDetailsViewHolder.itemNewsDetailBinding.imgBackIcon.setOnClickListener {
                (activity as ActDeshboardScreen).SctollLeft(0)
            }
            newsDetailsViewHolder.itemNewsDetailBinding.txtSubscription.setOnClickListener {
                (activity as ActDeshboardScreen).SubscribeNewsDialog()
            }

            newsDetailsViewHolder.itemNewsDetailBinding.imgVideoPlayIcon.setOnClickListener {
                Log.d("video_url", "video_url = " + newsData.videoPath)
                val vi = Intent(activity!!, ActVideoPlayer::class.java)
                vi.putExtra("video_url", newsData.videoPath)
                activity!!.startActivity(vi)
            }

            newsDetailsViewHolder.itemNewsDetailBinding.nighDayColor =
                Common.GetNightDayColor(activity)
            newsDetailsViewHolder.itemNewsDetailBinding.fontSize = Common.getFontSize(activity)
        }
    }

    inner class NewsHorizontalViewHolder(var itemNewHorizontalListBinding: ItemNewHorizontalListBinding) :
        RecyclerView.ViewHolder(itemNewHorizontalListBinding.root) {

    }

    inner class NewsDetailsViewHolder(var itemNewsDetailBinding: ItemNewsDetailsBinding) :
        RecyclerView.ViewHolder(itemNewsDetailBinding.root) {

    }

    fun MostViewNews(newsId: String) {
        WebAPIClient.getInstance(activity)
            .checkInternet()
            ?.MostNewsViews(newsId,
                object : Callback<CommonClass> {
                    override fun onFailure(call: Call<CommonClass>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<CommonClass>,
                        response: Response<CommonClass>
                    ) {

                        response.body()?.apply {

                        }
                    }
                })
    }
}