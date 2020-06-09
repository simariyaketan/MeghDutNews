package com.meghdut.news

import android.app.Application
import android.content.ContextWrapper
import com.meghdut.news.helper.IRoidLoader
import com.meghdut.news.model.category.CategoryData
import com.pixplicity.easyprefs.library.Prefs

class MyApplication : Application() {

    var categoryArrayList : ArrayList<CategoryData> = ArrayList<CategoryData>()

    companion object {
        private lateinit var app: MyApplication

        fun getInstance(): MyApplication? {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    private var iRoidLoader: IRoidLoader? = null

    fun showLoader() {

        if (iRoidLoader == null && app != null) {
            iRoidLoader = IRoidLoader(app!!)
        }

        if (iRoidLoader != null && !iRoidLoader!!.isShowing) {
            iRoidLoader?.show()
        }

    }

    fun logoutFromApp() {
        /*setUserOnlineOrOffline(isOnline = false)
        employeeListResponse = null
        businessStatsData = null
        complimentsListBusinessSide.clear()
        homeFeedArrayList.clear()
        myBusinessListData.clear()
        Prefs.putString(PreferenceField.LOGIN, "")
        val intent = Intent(applicationContext, ActChooseLogin::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)*/
    }
}
