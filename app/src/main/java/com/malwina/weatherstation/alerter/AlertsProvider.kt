package com.malwina.weatherstation.alerter

import android.app.Activity
import com.malwina.weatherstation.R
import com.tapadoo.alerter.Alert
import com.tapadoo.alerter.Alerter

object AlertsProvider {
    fun showErrorAlert(activity: Activity, errorMsg: String, icon: Int): Alert? {
        return Alerter.create(activity)
            .setText(errorMsg)
            .setBackgroundColorRes(R.color.error)
            .enableSwipeToDismiss()
            .enableInfiniteDuration(true)
            .setIcon(icon)
            .show()
    }
}