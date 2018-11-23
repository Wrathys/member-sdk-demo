package com.satayupomsri.membersdkdemo.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by satayupomsri on 23/11/2018 AD.
 */

internal fun isApplicationInstalled(context: Context, uri: String): Boolean {
    val pm = context.packageManager
    try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
    }

    return false
}
