package com.meghdut.news.utils

import android.app.Activity
import android.util.DisplayMetrics

object Common {
    fun getDeviceWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        return width
    }

}