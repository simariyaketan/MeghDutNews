package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.IRoidLoader
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.model.login.LoginClass
import com.meghdut.news.model.login.LoginData
import com.meghdut.news.network.WebAPIClient
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

abstract class ActBase : FragmentActivity() {

    private var weakReference: WeakReference<FragmentActivity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    val activity: FragmentActivity?
        get() {
            if (weakReference == null) {
                weakReference = WeakReference(this)
            }
            return weakReference?.get()
        }

    private var iRoidLoader: IRoidLoader? = null
    fun showLoader() {

        if (iRoidLoader == null && activity != null) {
            iRoidLoader = IRoidLoader(activity!!)
        }

        if (iRoidLoader != null && !iRoidLoader!!.isShowing) {
            iRoidLoader?.show()
        }

    }

    fun getSupportManager() : FragmentManager{
        return activity!!.supportFragmentManager
    }

    fun closeLoader() {
        try {
            if (iRoidLoader != null) {
                iRoidLoader?.dismiss()
                iRoidLoader = null
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (iRoidLoader != null) {
                iRoidLoader?.dismiss()
                iRoidLoader = null
            }
        }
    }

    fun getLoginData(): LoginData? {

        val loginResponse = Prefs.getString(PreferenceField.LOGIN, "")

        if (loginResponse.isNotEmpty()) {
            return Gson().fromJson(loginResponse, LoginData::class.java)
        }
        return null
    }

    fun getToken(): String {

        val token = Prefs.getString(PreferenceField.Token, "")

        if (token.isNotEmpty()) {
            return token
        }
        return ""
    }

    fun LogoutApplication() {
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.LogoutApiCall("", object : Callback<CommonClass> {
                override fun onFailure(call: Call<CommonClass>, t: Throwable) {
                    Log.d("error", "error = " + t.message!!)
                    Common.ShowErrorMessage(activity!!, t.message!!, null, 0)
                    closeLoader()
                }

                override fun onResponse(
                    call: Call<CommonClass>,
                    response: Response<CommonClass>
                ) {
                    closeLoader()
                    response.body()?.apply {
                        takeIf { status }?.let {
                            Common.ShowToast(activity!!, message)
                            val li = Intent(activity!!, ActLoginScreen::class.java)
                            li.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(li)
                        }.let {
                            Common.ShowToast(activity!!, message)
                        }
                    }
                }
            })
    }

    fun UnauthoriseUser(){
        val li = Intent(activity!!, ActLoginScreen::class.java)
        li.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(li)
    }

    fun LoginApiCall(mobile_nu: String, facebookId: String, name: String) {
        showLoader()
        val device_token = Prefs.getString(PreferenceField.DEVICE_TOKEN, "123")
        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.LoginApi(mobile_nu, name, facebookId, "Android", device_token,
                object : Callback<LoginClass> {
                    override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<LoginClass>,
                        response: Response<LoginClass>
                    ) {
                        closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {

                                Prefs.putString(
                                    PreferenceField.LOGIN,
                                    Gson().toJson(userdata)
                                )
                                Prefs.putString(
                                    PreferenceField.Token,
                                    token
                                )

                                val adi = Intent(activity, ActAppDetials::class.java)
                                startActivity(adi)
                                finish()

                            } ?: let {
                                Common.ShowToast(activity!!, message)
                            }
                        }

                    }

                })
    }
}