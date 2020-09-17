package com.malwina.weatherstation.alerter

import android.app.Activity
import com.tapadoo.alerter.Alert

interface IAlertsProvider {
    fun showErrorAlert(activity: Activity, errorMsg: String, icon: Int) : Alert?
}