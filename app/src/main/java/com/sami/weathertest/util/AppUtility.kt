package com.sami.weathertest.util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custom_toolbar_layout.*

/**
 * configure Custom Toolbar with title and back button
 */

fun configureHomeToolBar(
    activity: AppCompatActivity,
    isBackButtonVisible: Boolean
) {
    if (isBackButtonVisible){
        activity.img_back.visibility = View.VISIBLE
    }else{
        activity.img_back.visibility = View.GONE
    }
}

fun getConnectivityStatus(context: Context): Boolean {
    val cm = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return null != activeNetwork
}