package com.meghdut.news.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.meghdut.news.R
import com.meghdut.news.view.ActDeshboardScreen
import com.meghdut.news.view.ActNewsDetail
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "PushNotifService"
    lateinit var name: String
    lateinit var mNotificationManager: NotificationManager
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Token", "Token " + token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val jsonObj : JSONObject = JSONObject(remoteMessage.data.toString())
        Log.i("MyAndroidClass", jsonObj.toString())
       // ShowNotification(jsonObj)

    }

    fun ShowNotification(jsonObj : JSONObject) {
        val renNum: Random = Random()
        val notificationnum: Int = renNum.nextInt(80 - 65) + 65
        val ni: Intent = Intent(this, ActNewsDetail::class.java)
        ni.putExtra("news_id",jsonObj.getString("news_id"))
        val bitmap: Bitmap? =
            getBitmapFromURL(jsonObj.getString("image"))
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val bigPictureStyle =
            NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(getString(R.string.app_name))
        bigPictureStyle.setSummaryText(Html.fromHtml(jsonObj.getString("title")).toString())
        bigPictureStyle.bigPicture(bitmap)
        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, ni, 0)
        var mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(getApplicationContext(), "notify_001")
        mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        var bigText: NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
        bigText.bigText("")
       // bigText.setBigContentTitle("Today's Bible Verse")
       // bigText.setSummaryText("Text in detail")

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
        //mBuilder.setContentTitle("Your Title")
        //mBuilder.setContentText("Your text")
        mBuilder.setPriority(Notification.PRIORITY_MAX)
        mBuilder.setStyle(bigPictureStyle)
        mBuilder.setSound(defaultSoundUri)
        mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channelId: String = getString(R.string.notification_channel_id);
            var channel: NotificationChannel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            );
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(notificationnum, mBuilder.build())
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    fun getBitmapFromURL(strURL: String): Bitmap? {
        return try {
            val url = URL(strURL)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Clears notification tray messages
    fun clearNotifications() {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    fun getTimeMilliSec(timeStamp: String): Long {
        val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            var date: Date = format.parse(timeStamp);
            return date.getTime()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

}