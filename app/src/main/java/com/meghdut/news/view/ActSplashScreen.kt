package com.meghdut.news.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.meghdut.news.R
import com.meghdut.news.helper.PreferenceField
import com.pixplicity.easyprefs.library.Prefs
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class ActSplashScreen : ActBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_splash_screen)


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Token", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Prefs.putString(PreferenceField.DEVICE_TOKEN,token)
                Log.d("Token", token)
                //Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
            })
        printHashKey(this)
        Handler().postDelayed({

            if (Prefs.getString(PreferenceField.LOGIN_SUCCESS, "No").equals("Yes")) {
                val li = Intent(activity, ActDeshboardScreen::class.java)
                startActivity(li)
                finish()
            } else {
                val li = Intent(activity, ActLoginScreen::class.java)
                startActivity(li)
                finish()
            }
        }, 1000)
    }


    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("printHashKey", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("printHashKey", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("printHashKey", "printHashKey()", e)
        }
    }

}
