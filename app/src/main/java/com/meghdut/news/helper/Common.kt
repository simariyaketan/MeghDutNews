package com.meghdut.news.helper

import android.Manifest
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.meghdut.news.R
import com.meghdut.news.model.FontSize
import com.meghdut.news.model.NightDayColor
import com.pixplicity.easyprefs.library.Prefs
import java.io.File
import java.sql.Time
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object Common {

    val OTP_API_KEY = "a7151f52-9f82-11ea-9fa5-0200cd936042"
    fun ShowToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun ShowErrorMessage(activity: Activity, Msg: String, internetView: View?, offset: Int) {
        Log.d("Msg", "Msg = " + Msg)
        var errorMessage: String = "Somthing went wrong."
        if (Msg.toLowerCase().contains("failed to connect to") || Msg.toLowerCase().contains("unable to resolve hos")) {
            errorMessage = "No Interner connection."
            if (internetView != null && offset == 0)
                internetView.visibility = View.VISIBLE
        } else if (Msg.toLowerCase().contains("http 500 internal server error"))
            errorMessage = "HTTP 500 Internal Server Error"
        else if (Msg.toLowerCase().contains("http 404"))
            errorMessage = "HTTP 404 Error"

        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }

    fun isInternetConnectedCheck(mContext: Context?): Boolean {
        var outcome = false

        try {
            if (mContext != null) {
                val cm = mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkInfos = cm.allNetworkInfo

                for (tempNetworkInfo in networkInfos) {

                    if (tempNetworkInfo.isConnected) {
                        outcome = true
                        break
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return outcome
    }

    fun GeranteRendomNum(): String {
        var rendomNum: String = ""
        var random: Random = Random()
        rendomNum = String.format("%04d", random.nextInt(10000))
        return rendomNum
    }

    fun getCacheSize(activity: Activity): String {
        var size: String = "0"
        var files: File = activity.getCacheDir()
        var filesize: Long = files.length()
        Log.d("error", "error 4 = " + filesize)
        return FileSizeToMb(filesize)
    }

    fun FileSizeToMb(filesize: Long): String {
        if (filesize <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        var digitGroups: Int =
            ((Math.log10(filesize.toDouble()) / Math.log10(1024.toDouble())).toInt())

        return DecimalFormat("#,##0.#").format(
            filesize / Math.pow(
                1024.toDouble(),
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }

    /*Delete Cache derectory*/
    fun deleteCache(activity: Activity): String {
        try {
            val dir: File = activity.getCacheDir()
            Log.d("error", "error 1 = " + dir.length())
            deleteDir(dir)
            Log.d("error", "error 2 = " + FileSizeToMb(dir.length()))
            return FileSizeToMb(dir.length())
        } catch (e: java.lang.Exception) {
            Log.d("error", "error 3 = " + e.message)
            e.printStackTrace()
            return "0"
        }
    }

    fun deleteDir(dir: File): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success: Boolean = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    fun youDesirePermissionCode(context: Activity) {
        val permission: Boolean
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(context)
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_SETTINGS
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (permission) { //do your code
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivityForResult(intent, 0)
            } else {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.WRITE_SETTINGS),
                    0
                )
            }
        }
    }

    fun OpenTimePicker(activity: Activity, dateTimeEdit: EditText) {
        val c: Calendar = Calendar.getInstance()
        val hh = c.get(Calendar.HOUR_OF_DAY)
        val mm = c.get(Calendar.MINUTE)
        val timePickerDialog: TimePickerDialog = TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                val time: String = getTime(hourOfDay, minute)
                dateTimeEdit.setText(time)
            },
            hh,
            mm,
            false
        )
        timePickerDialog.show()
    }

    fun getTime(hr: Int, min: Int): String {
        val tme: Time = Time(hr, min, 0)
        val formatter: SimpleDateFormat = SimpleDateFormat("h:mm a")
        return formatter.format(tme)
    }

    fun getDeviceWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        return width
    }

    fun getFontSize(activity: Activity): FontSize {
        val fontSize: FontSize = FontSize()

        if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Regular")) {
            fontSize.font10 = activity.resources.getDimension(R.dimen.font_10)
            fontSize.font12 = activity.resources.getDimension(R.dimen.font_12)
            fontSize.font14 = activity.resources.getDimension(R.dimen.font_14)
            fontSize.font16 = activity.resources.getDimension(R.dimen.font_16)
            fontSize.font18 = activity.resources.getDimension(R.dimen.font_18)
            fontSize.font20 = activity.resources.getDimension(R.dimen.font_20)
            fontSize.font22 = activity.resources.getDimension(R.dimen.font_22)
            fontSize.font24 = activity.resources.getDimension(R.dimen.font_24)
            fontSize.font26 = activity.resources.getDimension(R.dimen.font_26)
        } else if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Small")) {
            fontSize.font10 = activity.resources.getDimension(R.dimen.font_8)
            fontSize.font12 = activity.resources.getDimension(R.dimen.font_10)
            fontSize.font14 = activity.resources.getDimension(R.dimen.font_12)
            fontSize.font16 = activity.resources.getDimension(R.dimen.font_14)
            fontSize.font18 = activity.resources.getDimension(R.dimen.font_16)
            fontSize.font20 = activity.resources.getDimension(R.dimen.font_18)
            fontSize.font22 = activity.resources.getDimension(R.dimen.font_20)
            fontSize.font24 = activity.resources.getDimension(R.dimen.font_22)
            fontSize.font26 = activity.resources.getDimension(R.dimen.font_24)

        }else if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Big")) {
            fontSize.font10 = activity.resources.getDimension(R.dimen.font_12)
            fontSize.font12 = activity.resources.getDimension(R.dimen.font_14)
            fontSize.font14 = activity.resources.getDimension(R.dimen.font_16)
            fontSize.font16 = activity.resources.getDimension(R.dimen.font_18)
            fontSize.font18 = activity.resources.getDimension(R.dimen.font_20)
            fontSize.font20 = activity.resources.getDimension(R.dimen.font_22)
            fontSize.font22 = activity.resources.getDimension(R.dimen.font_24)
            fontSize.font24 = activity.resources.getDimension(R.dimen.font_26)
            fontSize.font26 = activity.resources.getDimension(R.dimen.font_28)

        }


        return fontSize
    }

    fun GetNightDayColor(activity: Activity) : NightDayColor {
        val nightDayColor : NightDayColor = NightDayColor()
        if (Prefs.getString(PreferenceField.NIGHT_NODE, "").equals("On")) {
            nightDayColor.background_color = ContextCompat.getColor(activity, R.color.color_4d4d4d)
            nightDayColor.white_text_color = ContextCompat.getColor(activity, R.color.color_ffffff)
            nightDayColor.gray_line_color =
                ContextCompat.getColor(activity, R.color.color_747474) //color_20272727
            nightDayColor.setting_gray_line_color =
                ContextCompat.getColor(activity, R.color.color_747474) //color_B8B8B8
            nightDayColor.gray_text_color =
                ContextCompat.getColor(activity, R.color.color_ffffff) // color_737373
            nightDayColor.black_text_color =
                ContextCompat.getColor(activity, R.color.color_ffffff) // color_000000
            nightDayColor.text_color_222222 =
                ContextCompat.getColor(activity, R.color.color_ffffff) // color_222222
            nightDayColor.text_color_222222 =
                ContextCompat.getColor(activity, R.color.color_ffffff) // color_222222
            nightDayColor.text_color_272727 =
                ContextCompat.getColor(activity, R.color.color_ffffff) // color_272727
        }else{
            nightDayColor.background_color = ContextCompat.getColor(activity, R.color.color_ffffff)
            nightDayColor.white_text_color = ContextCompat.getColor(activity, R.color.color_272727)
            nightDayColor.gray_line_color =
                ContextCompat.getColor(activity, R.color.color_20272727) //color_20272727
            nightDayColor.setting_gray_line_color =
                ContextCompat.getColor(activity, R.color.color_B8B8B8) //color_B8B8B8
            nightDayColor.gray_text_color =
                ContextCompat.getColor(activity, R.color.color_737373) // color_737373
            nightDayColor.black_text_color =
                ContextCompat.getColor(activity, R.color.color_000000) // color_000000
            nightDayColor.text_color_222222 =
                ContextCompat.getColor(activity, R.color.color_222222) // color_222222
            nightDayColor.text_color_222222 =
                ContextCompat.getColor(activity, R.color.color_222222) // color_222222
            nightDayColor.text_color_272727 =
                ContextCompat.getColor(activity, R.color.color_272727) // color_272727
        }
        return nightDayColor
    }
}