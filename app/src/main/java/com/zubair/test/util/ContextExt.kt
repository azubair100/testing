package com.zubair.test.util

import android.app.AlertDialog
import android.content.Context
import com.zubair.test.R

fun Context.buildErrorDialog() {
    val errorAlert = AlertDialog.Builder(this)
        .setTitle(getString(R.string.error_alert_title))
        .setMessage(getString(R.string.error_alert_message))
        .create()

    errorAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) {
        dialog, _ -> dialog.dismiss()
    }

    errorAlert.show()

}