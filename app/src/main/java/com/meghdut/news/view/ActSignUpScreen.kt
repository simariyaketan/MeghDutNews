package com.meghdut.news.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.meghdut.news.R
import com.meghdut.news.databinding.ActSignUpScreenBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.network.WebAPIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActSignUpScreen : ActBase() {
    lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(getApplicationContext())
        val actSignUpScreenBinding: ActSignUpScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_sign_up_screen)

        actSignUpScreenBinding.linFooter.setOnClickListener {
            finish()
        }

        actSignUpScreenBinding.txtSignUp.setOnClickListener {

            if (actSignUpScreenBinding.edtRegUserName.text.toString().length == 0) {
                Common.ShowToast(this, getString(R.string.please_enter_name))
                return@setOnClickListener
            } else if (actSignUpScreenBinding.edtRegMobileNo.text.toString().length == 0) {
                Common.ShowToast(this, getString(R.string.please_enter_mobile_name))
                return@setOnClickListener
            } else if (actSignUpScreenBinding.edtRegMobileNo.text.toString().length <= 9) {
                Common.ShowToast(this, getString(R.string.mobile_number_length))
                return@setOnClickListener
            }

            RegisterApiCall(
                actSignUpScreenBinding.edtRegUserName.text.toString(),
                actSignUpScreenBinding.edtRegMobileNo.text.toString()
            )
        }

        callbackManager = CallbackManager.Factory.create()
        actSignUpScreenBinding.txtSignUpWithFB.setOnClickListener {
            val loggedIn: Boolean = AccessToken.getCurrentAccessToken() != null
            if (loggedIn) {
                val accessToken: AccessToken = AccessToken.getCurrentAccessToken()
                FacebookResponse(accessToken)
            } else {
                LoginManager.getInstance().logInWithPublishPermissions(
                    activity!!,
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

    private fun RegisterApiCall(name: String, mobile_nu: String) {
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.RegisterApi(name, mobile_nu, "",
                object : Callback<CommonClass> {
                    override fun onFailure(call: Call<CommonClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
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
                                finish()
                            } ?: Common.ShowToast(activity!!, message)
                        }

                    }

                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
