package com.meghdut.news.network

import com.meghdut.news.model.category.CategoryClass
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.model.comonclass.OtpClass
import com.meghdut.news.model.login.LoginClass
import com.meghdut.news.model.news.NewsClass
import com.meghdut.news.model.news.NewsDetailClass
import retrofit2.Call
import retrofit2.http.*

interface WebAPIInterface {

    @FormUrlEncoded
    @POST("requestotp")
    fun RequestOTPApiCall(
        @Field("mobile_no") mobile_no: String): Call<LoginClass>

    @FormUrlEncoded
    @POST("optvarification")
    fun verifyOTP(
        @Field("session_id") session_id: String,
        @Field("otp") otp_input: String
    ): Call<LoginClass>

    @FormUrlEncoded
    @POST("login")
    fun LoginApiCall(
        @Field("mobile_no") mobile_no: String,
        @Field("name") name: String,
        @Field("facebook_id") facebook_id: String,
        @Field("device") device: String,
        @Field("device_token") device_token: String
    ): Call<LoginClass>

    @FormUrlEncoded
    @POST("register")
    fun RegisterApiCall(
        @Field("name") name: String,
        @Field("mobile_no") mobile_no: String,
        @Field("facebook_id") facebook_id: String
    ): Call<CommonClass>

    @FormUrlEncoded
    @POST("list/category")
    fun GetCategoryApiCall(
        @Field("id") token: String
    ): Call<CategoryClass>

    @FormUrlEncoded
    @POST("add/category")
    fun AddSelectedCategory(
        @Field("categiries") categiries: String
    ): Call<CommonClass>

    @FormUrlEncoded
    @POST("add/notification")
    fun AddSelectedNotification(
        @Field("noti_catagories") notiCatagories: String,
        @Field("noti_time_from") notiTimeFrom: String,
        @Field("noti_time_to") notiTimeTo: String
    ): Call<CommonClass>

    @FormUrlEncoded
    @POST("list/news")
    fun getNewsList(
        @Field("perpage") perpage: Int,
        @Field("offset") offset: Int
    ): Call<NewsClass>

    @FormUrlEncoded
    @POST("mostviewnews")
    fun AddMostViewNews(
        @Field("news_id") news_id: String
    ): Call<CommonClass>

    @FormUrlEncoded
    @POST("subscription")
    fun SubscribeNews(
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<CommonClass>

    @FormUrlEncoded
    @POST("addfavorite")
    fun AddToFavoriteNews(
        @Field("news_id") news_id: String
    ): Call<CommonClass>

    @GET("{api_key}/SMS/+91{users_phone_no}/{otp}")
    fun SendOtp(
        @Path("api_key") apiKey: String,
        @Path("users_phone_no") phone_no: String,
        @Path("otp") otp: String
    ): Call<OtpClass>

    @FormUrlEncoded
    @POST("list/newsdetail")
    fun GetNewsDetail(
        @Field("newsid") newsid: String
    ): Call<NewsDetailClass>

    @FormUrlEncoded
    @POST("list/favouritenews")
    fun getFavouriteNewsList(
        @Field("perpage") perpage: Int,
        @Field("offset") offset: Int
    ): Call<NewsClass>

    @FormUrlEncoded
    @POST("logout")
    fun LogoutApp(@Field("id") id: String): Call<CommonClass>


}