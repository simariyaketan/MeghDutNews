package com.meghdut.news.model.comonclass

import com.google.gson.annotations.SerializedName

data class CommonClass(
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("code")
    var code: Int = 0
)