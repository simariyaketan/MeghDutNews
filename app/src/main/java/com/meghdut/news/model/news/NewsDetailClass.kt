package com.meghdut.news.model.news

import com.google.gson.annotations.SerializedName

data class NewsDetailClass(
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("list")
    var newsData: NewsData = NewsData()
)