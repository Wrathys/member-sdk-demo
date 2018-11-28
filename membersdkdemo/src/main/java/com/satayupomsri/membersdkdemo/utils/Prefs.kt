package com.satayupomsri.membersdkdemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.satayupomsri.membersdkdemo.protocol.ServerProtocol

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
internal class Prefs (context: Context) {

    private val prefName = getKey(context, ServerProtocol.prefName, ServerProtocol.PREF_NAME_ID)
    private val prefField = getKey(context, ServerProtocol.prefName, ServerProtocol.PREF_FIELD_ID)

    private val prefs: SharedPreferences = context.getSharedPreferences(prefName, 0)

    var jwt: String?
        get() = prefs.getString(prefField, null)
        set(jwt) {
            if(jwt == null)
                prefs.edit().remove(prefField).apply()
            else
                prefs.edit().putString(prefField, jwt).apply()
        }
}