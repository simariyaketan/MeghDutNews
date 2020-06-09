package com.meghdut.news.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginClass (
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("token")
    var token: String = "",
    @SerializedName("user")
    var userdata: UserData = UserData()
)

data class LoginData(
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("token")
    var token: String = "",
    @SerializedName("user")
    var userdata: UserData = UserData()
)

data class UserData(
    @SerializedName("state")
    var state: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("user_id")
    var userId: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("mobile_no")
    var mobileNo: String = "",
    @SerializedName("whatsapp_no")
    var whatsappNo: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("liecence")
    var liecence: String = "",
    @SerializedName("adharcard")
    var adharcard: String = "",
    @SerializedName("facebook_id")
    var facebookId: String = ""
)