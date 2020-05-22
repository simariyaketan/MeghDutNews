package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.databinding.ActOtpScreenBinding

class ActOtpScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actOtpScreenBinding: ActOtpScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_otp_screen)

        actOtpScreenBinding.imgBack.setOnClickListener {
            finish()
        }



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
                    val adi = Intent(this@ActOtpScreen, ActAppDetials::class.java)
                    startActivity(adi)
                    finish()
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
}
