package com.meghdut.news.utils

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.meghdut.news.R
import com.meghdut.news.network.WebAPIClient
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

object CommonDataBinding {

    @JvmStatic
    @BindingAdapter("newsImage")
    fun NewsImages(
        imageView: ImageView,
        imageUrl: String
    ) {
        val NewsImgUrl = WebAPIClient.imagebaseUrl + imageUrl
        Log.d("imagePath", "imagePath = $NewsImgUrl")
        Picasso.get()
            .load(Uri.parse(NewsImgUrl))
            .fit()
            .into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    Log.d("imagePath", "imagePath = Error" + e!!.message)
                }

            })
    }

    @JvmStatic
    @BindingAdapter("categoryImage")
    fun CategoryImages(
        imageView: ImageView,
        imageUrl: String
    ) {
        val NewsImgUrl = WebAPIClient.imagebaseUrl + imageUrl
        Log.d("imagePath", "imagePath = $NewsImgUrl")
        Picasso.get()
            .load(Uri.parse(NewsImgUrl))
            .fit()
            .into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    Log.d("imagePath", "imagePath = Error" + e!!.message)
                }

            })
    }
}