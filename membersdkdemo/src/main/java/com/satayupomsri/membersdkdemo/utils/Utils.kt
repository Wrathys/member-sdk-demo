package com.satayupomsri.membersdkdemo.utils

import android.content.Context
import android.content.pm.PackageManager
import com.satayupomsri.membersdkdemo.protocol.ServerProtocol
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by satayupomsri on 23/11/2018 AD.
 */

internal fun isApplicationInstalled(context: Context, uri: String): Boolean {
    val pm = context.packageManager
    try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        return true
    } catch (e: PackageManager.NameNotFoundException) {}

    return false
}

internal fun getKey(context: Context, serverDataName: String, apiId: Int): String {
    var reader: BufferedReader? = null
    var api = ""

    try {
        reader = BufferedReader(InputStreamReader(context.assets.open("$serverDataName.${ServerProtocol.ext}"), "UTF-8"))

        val lines: List<String> = reader.readLines()
        api = lines[apiId]
    } catch (e: IOException) {} finally {
        reader?.close()
    }

    return api
}
