package com.meghdut.news.network

import android.content.Context
import android.os.Debug
import android.util.Log
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.category.CategoryClass
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.model.comonclass.OtpClass
import com.meghdut.news.model.login.LoginClass
import com.meghdut.news.model.news.NewsClass
import com.meghdut.news.model.news.NewsDetailClass
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebAPIClient(private val jsonClient: Retrofit, private val activity: Context?) {

    private fun json(): WebAPIInterface {
        return jsonClient.create(WebAPIInterface::class.java)
    }

    companion object {

        var websiteUrl = "http://atdeveloper.xyz/meghdut/"
        private var baseUrl = "http://atdeveloper.xyz/meghdut/api/"
        var imagebaseUrl = "http://atdeveloper.xyz/meghdut/"

        fun getInstance(c: Context?): WebAPIClient {

            val builder = getHttpClientBuilder(c)
            builder?.addInterceptor { chain ->
                val isLogInUrl =
                    chain.request().url().toString().contains("register") || chain.request().url().toString().contains(
                        "login"
                    )

                if (isLogInUrl) {
                    AutoRetryRequest.getInstance()?.addAutoRetryIntercept(chain)
                } else {
                    Log.d(
                        "Authorization",
                        "Authorization = " + Prefs.getString(PreferenceField.Token, "")
                    )
                    val builder1 = chain.request().newBuilder()

                    builder1.addHeader(
                        "Authorization",
                        "Bearer " + Prefs.getString(PreferenceField.Token, "")
                    )
                    chain.proceed(builder1.build())

                }
            }

            val httpClient = builder?.build()


            val json = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            Log.d("json", " json = " + json)
            return WebAPIClient(json, c)

        }

        private fun getHttpClientBuilder(c: Context?): OkHttpClient.Builder? {
            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(60, TimeUnit.MINUTES)
            builder.connectTimeout(60, TimeUnit.MINUTES)

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
            return builder
        }
    }

    fun checkInternet(): WebAPIClient? {
        if (!Common.isInternetConnectedCheck(activity)) {
            return null
        }
        return this
    }

    /*Request OTP*/
    fun RequestOTPApi(
        mobile_no: String,
        callback: Callback<LoginClass>
    ) {
        json().RequestOTPApiCall(mobile_no).enqueue(callback)
    }

    /*Veryfy OTP*/
    fun verifyOTP(
        user_token: String,
        otp_input: String,
        callback: Callback<LoginClass>
    ) {
        json().verifyOTP(user_token, otp_input).enqueue(callback)
    }

    /*Login Api Call*/
    fun LoginApi(
        mobile_no: String,name: String, facebook_id: String, device: String, device_token: String,
        callback: Callback<LoginClass>
    ) {
        json().LoginApiCall(mobile_no,name, facebook_id, device, device_token).enqueue(callback)
    }

    /*Register Api Call*/
    fun RegisterApi(
        name: String, mobile_no: String, facebook_id: String,
        callback: Callback<CommonClass>
    ) {
        json().RegisterApiCall(name, mobile_no, facebook_id).enqueue(callback)
    }

    /*Get Category List*/
    fun GetCategoryList(
        id: String,
        callback: Callback<CategoryClass>
    ) {
        json().GetCategoryApiCall(id).enqueue(callback)
    }

    /*Add Category*/
    fun AddSelectedCategory(
        categiries: String,
        callback: Callback<CommonClass>
    ) {
        json().AddSelectedCategory(categiries).enqueue(callback)
    }

    /*Add Notification*/
    fun AddSelectedNotification(
        notification: String,
        noti_time_from: String,
        noti_time_to: String,
        callback: Callback<CommonClass>
    ) {
        json().AddSelectedNotification(notification, noti_time_from, noti_time_to).enqueue(callback)
    }

    /*News List*/
    fun getNews(
        perpage: Int,
        offset: Int,
        callback: Callback<NewsClass>
    ) {
        json().getNewsList(perpage, offset).enqueue(callback)
    }

    /*Favorite News List*/
    fun getFavoriteNews(
        perpage: Int,
        offset: Int,
        callback: Callback<NewsClass>
    ) {
        json().getNewsList(perpage, offset).enqueue(callback)
    }

    /*Most view news*/
    fun MostNewsViews(news_id: String, callback: Callback<CommonClass>) {
        json().AddMostViewNews(news_id).enqueue(callback)
    }

    /*Subscribe news*/
    fun SubscribeNews(name: String, email: String, callback: Callback<CommonClass>) {
        json().SubscribeNews(name, email).enqueue(callback)
    }

    /*Add To Favorite News*/
    fun AddToFavoriteNews(newsId: String, callback: Callback<CommonClass>) {
        json().AddToFavoriteNews(newsId).enqueue(callback)
    }

    /*Send OTP*/
    fun SendOTP(
        api_key: String,
        users_phone_no: String,
        otp: String,
        callback: Callback<OtpClass>
    ) {
        json().SendOtp(api_key, users_phone_no, otp).enqueue(callback)
    }

    /*Logout api call*/
    fun LogoutApiCall(id: String, callback: Callback<CommonClass>) {
        json().LogoutApp(id).enqueue(callback)
    }

    /*Get News Detail*/
    fun GetNesDetail(newsid: String, callback: Callback<NewsDetailClass>) {
        json().GetNewsDetail(newsid).enqueue(callback)
    }
}