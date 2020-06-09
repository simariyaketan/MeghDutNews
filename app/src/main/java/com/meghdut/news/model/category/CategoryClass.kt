package com.meghdut.news.model.category

import com.google.gson.annotations.SerializedName

data class CategoryClass(
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("code")
    var code: Int = 0,
    @SerializedName("list")
    var list: ArrayList<CategoryData> = arrayListOf()
)

data class CategoryData(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("icon")
    var icon: String = "",
    @SerializedName("favorite")
    var favorite: String = "",
    @SerializedName("notification")
    var notification: String = "",
    var isSelected: Boolean = false
)