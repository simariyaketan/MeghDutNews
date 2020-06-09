package com.meghdut.news.model.comonclass

import com.google.gson.annotations.SerializedName

data class OtpClass(
    @SerializedName("Status")
    var status: String = "",
    @SerializedName("Details")
    var Details: String = ""
)