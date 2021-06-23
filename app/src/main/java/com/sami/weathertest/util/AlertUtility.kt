package com.sami.weathertest.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.View
import com.sami.weathertest.R
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*


object AlertUtility {
    /**
     * * Show custom alert with title and Neutral button and a callback in click action
     * @param activity
     * @param title
     * @param message
     * @param textButton
     * @param buttonCallBack
     */
    @SuppressLint("InflateParams")
    fun showNeutralAlert (activity: Activity?, title: String?, message: String, textButton: String, buttonCallBack: NoticeDialogListener, isBlocking: Boolean ) {

        if (activity != null && !activity.isFinishing) {
            val dialogView = activity.layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            dialogView.message_custom_alert.text = message
            dialogView.positive_btn_custom_alert.text = textButton
            dialogView.positive_btn_custom_alert.textAlignment = View.TEXT_ALIGNMENT_CENTER

            if (title != null && title.isNotEmpty()) {
                dialogView.title_custom_alert.visibility = View.VISIBLE
                dialogView.title_custom_alert.text = title
            } else {
                dialogView.title_custom_alert.visibility = View.GONE
            }

            val alertDialog = AlertDialog.Builder(activity)
                .setCancelable(isBlocking)
                .setView(dialogView)
                .create()

            dialogView.positive_btn_custom_alert.setOnClickListener {
                buttonCallBack.onButtonClick(
                    alertDialog
                )
            }
            alertDialog.setCanceledOnTouchOutside(isBlocking)
            alertDialog.show()
        }
    }


}