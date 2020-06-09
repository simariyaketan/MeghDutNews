package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.meghdut.news.R
import com.meghdut.news.databinding.ActOtpScreenBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.login.LoginClass
import com.meghdut.news.model.login.UserData
import com.meghdut.news.network.WebAPIClient
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActOtpScreen : ActBase() {

    var user_token: String = ""
    lateinit var mobile_number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actOtpScreenBinding: ActOtpScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_otp_screen)

        user_token = intent.getStringExtra("token")
        mobile_number = intent.getStringExtra("mobile_number")

        actOtpScreenBinding.imgBack.setOnClickListener {
            finish()
        }

        actOtpScreenBinding.txtMobileNum.setText("+91" + mobile_number)


        actOtpScreenBinding.edtOtpOne.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("Editable", "Editable = " + p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("Editable", "Editable = " + p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (actOtpScreenBinding.edtOtpOne.length() == 1) {
                    actOtpScreenBinding.edtOtpOne.clearFocus()
                    actOtpScreenBinding.edtOtpTwo.requestFocus()
                    actOtpScreenBinding.edtOtpTwo.setCursorVisible(true)
                }
            }

        })

        actOtpScreenBinding.edtOtpTwo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("Editable", "Editable = " + p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("Editable", "Editable = " + p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (actOtpScreenBinding.edtOtpTwo.length() == 1) {
                    actOtpScreenBinding.edtOtpTwo.clearFocus()
                    actOtpScreenBinding.edtOtpThree.requestFocus()
                    actOtpScreenBinding.edtOtpThree.setCursorVisible(true)
                }
            }

        })

        var previewEdit: Boolean = false
        actOtpScreenBinding.edtOtpTwo.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, p2: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (actOtpScreenBinding.edtOtpTwo.text.toString().length == 0) {
                        if (previewEdit) {
                            actOtpScreenBinding.edtOtpOne.requestFocus()
                            actOtpScreenBinding.edtOtpOne.setCursorVisible(true)
                            previewEdit = false
                        } else
                            previewEdit = true
                    }
                }
                return false
            }

        })

        actOtpScreenBinding.edtOtpThree.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (actOtpScreenBinding.edtOtpThree.length() == 1) {
                    actOtpScreenBinding.edtOtpThree.clearFocus()
                    actOtpScreenBinding.edtOtpFour.requestFocus()
                    actOtpScreenBinding.edtOtpFour.setCursorVisible(true)
                }
            }

        })

        actOtpScreenBinding.edtOtpThree.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, p2: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (actOtpScreenBinding.edtOtpThree.text.toString().length == 0) {
                        if (previewEdit) {
                            actOtpScreenBinding.edtOtpTwo.requestFocus()
                            actOtpScreenBinding.edtOtpTwo.setCursorVisible(true)
                            previewEdit = false
                        } else
                            previewEdit = true
                    }
                }
                return false
            }

        })

        actOtpScreenBinding.edtOtpFour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (actOtpScreenBinding.edtOtpFour.length() == 1) {
                    actOtpScreenBinding.edtOtpFour.clearFocus()

                    var otp_input: String =
                        actOtpScreenBinding.edtOtpOne.text.toString() + actOtpScreenBinding.edtOtpTwo.text.toString() + actOtpScreenBinding.edtOtpThree.text.toString() + actOtpScreenBinding.edtOtpFour.text.toString()
                    VeryfyOTP(otp_input)
                }
            }

        })

        actOtpScreenBinding.edtOtpFour.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, p2: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (actOtpScreenBinding.edtOtpFour.text.toString().length == 0) {
                        if (previewEdit) {
                            actOtpScreenBinding.edtOtpThree.requestFocus()
                            actOtpScreenBinding.edtOtpThree.setCursorVisible(true)
                            previewEdit = false
                        } else
                            previewEdit = true
                    }
                }
                return false
            }

        })
    }


    private fun VeryfyOTP(otp_input: String) {

        showLoader()
        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.verifyOTP(user_token, otp_input,
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

                                LoginApiCall(mobile_number)


                            } ?: let {
                                closeLoader()
                                Common.ShowToast(activity!!, message)
                            }
                        }
                    }
                })

    }

    private fun LoginApiCall(mobile_nu: String) {
        val device_token = Prefs.getString(PreferenceField.DEVICE_TOKEN,"123")
        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.LoginApi(mobile_nu, "","", "Android", device_token,
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

                                val adi = Intent(this@ActOtpScreen, ActAppDetials::class.java)
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
