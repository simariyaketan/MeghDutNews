package com.meghdut.news.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import com.meghdut.news.R
import com.meghdut.news.databinding.ActLoginScreenBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.login.LoginClass
import com.meghdut.news.network.WebAPIClient
import com.pixplicity.easyprefs.library.Prefs
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActLoginScreen : ActBase() {

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(getApplicationContext())
        val actLoginScreenBinding: ActLoginScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_login_screen)


        actLoginScreenBinding.txtSignIn.setOnClickListener {
            if (actLoginScreenBinding.edtMobileNo.text.toString().length == 0) {
                Common.ShowToast(this, getString(R.string.please_enter_mobile_name))
                return@setOnClickListener
            } else if (actLoginScreenBinding.edtMobileNo.text.toString().length <= 9) {
                Common.ShowToast(this, getString(R.string.mobile_number_length))
                return@setOnClickListener
            } else if (!actLoginScreenBinding.chkTermsCond.isChecked) {
                Common.ShowToast(activity!!, getString(R.string.please_select_terms_condition))
                return@setOnClickListener
            }

            //LoginApiCall(actLoginScreenBinding.edtMobileNo.text.toString())
            RequestOTP(actLoginScreenBinding.edtMobileNo.text.toString())

        }

        actLoginScreenBinding.lindontHaveAccount.setOnClickListener {
            val li = Intent(this, ActSignUpScreen::class.java)
            startActivity(li)
        }

        actLoginScreenBinding.linFooter.setOnClickListener {
            val uris = Uri.parse(WebAPIClient.websiteUrl + "terms-and-condition")
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)
        }

        callbackManager = CallbackManager.Factory.create()

        actLoginScreenBinding.txtLoginWithFB.setOnClickListener {

            if (!actLoginScreenBinding.chkTermsCond.isChecked) {
                Common.ShowToast(activity!!, getString(R.string.please_select_terms_condition))
                return@setOnClickListener
            }

            val loggedIn: Boolean = AccessToken.getCurrentAccessToken() != null
            if (loggedIn) {
                val accessToken: AccessToken = AccessToken.getCurrentAccessToken()
                FacebookResponse(accessToken)
            } else {
                LoginManager.getInstance().logInWithPublishPermissions(
                    this@ActLoginScreen,
                    Arrays.asList("publish_actions")
                )

                LoginManager.getInstance()
                    .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult?) {
                            Log.d("loginResult", "loginResult = $loginResult")
                            FacebookResponse(loginResult?.accessToken!!)
                        }

                        override fun onCancel() {

                        }

                        override fun onError(error: FacebookException?) {

                        }

                    })
            }


        }

    }

    fun FacebookResponse(accessToken: AccessToken) {
        val request: GraphRequest = GraphRequest.newMeRequest(
            accessToken, object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
                    Log.d("object", "object = $obj==$response")

                    val facebookId: String = obj!!.getString("id")
                    var name: String = ""
                    if (obj.has("name"))
                        name = obj.getString("name")

                    LoginApiCall("", facebookId, name)
                }

            })

        val parameters: Bundle = Bundle()
        parameters.putString("fields", "id,name,email")
        request.setParameters(parameters)
        request.executeAsync()
    }

    private fun RequestOTP(mobile_nu: String) {
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.RequestOTPApi(mobile_nu,
                object : Callback<LoginClass> {
                    override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<LoginClass>,
                        response: Response<LoginClass>
                    ) {

                        response.body()?.apply {
                            takeIf { status }?.let {

                                //SendOTP(userdata, token, mobile_nu)
                                val pi: Intent = Intent(activity, ActOtpScreen::class.java)
                                pi.putExtra("token", token)
                                pi.putExtra("mobile_number", mobile_nu)
                                startActivity(pi)

                            } ?: let {
                                closeLoader()
                                Common.ShowToast(activity!!, message)
                            }
                        }

                    }

                })
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
