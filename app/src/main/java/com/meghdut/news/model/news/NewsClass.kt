package com.meghdut.news.model.news

import com.google.gson.annotations.SerializedName

data class NewsClass(
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("code")
    var code: Int = 0,
    @SerializedName("list")
    var list: ArrayList<NewsData> = arrayListOf()
)

data class NewsData(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("newsid")
    var newsid: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("category")
    var category: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("source")
    var source: String = "",
    @SerializedName("publisher")
    var publisher: String = "",
    @SerializedName("tags")
    var tags: String = "",
    @SerializedName("short_content")
    var shortContent: String = "",
    @SerializedName("content")
    var content: String = "",
    @SerializedName("publish_date")
    var publishDate: String = "",
    @SerializedName("meta_title")
    var metaTitle: String = "",
    @SerializedName("meta_keyword")
    var metaKeyword: String = "",
    @SerializedName("meta_desc")
    var metaDesc: String = "",
    @SerializedName("publish")
    var publish: String = "",
    @SerializedName("send_noti_all")
    var sendNotiAll: String = "",
    @SerializedName("file_type")
    var fileType: String = "",
    @SerializedName("file")
    var file: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("blur_image")
    var blurImage: String = "",
    @SerializedName("publish_date_time")
    var publishDateTime: String = "",
    @SerializedName("viewed")
    var viewed: String = "",
    @SerializedName("share_url")
    var shareUrl: String = "",
    @SerializedName("app_title")
    var appTitle: String = "",
    @SerializedName("video_path")
    var videoPath: String = "",
    @SerializedName("favourite")
    var favourite: String = "",
    var footerTitle: String = ""
)