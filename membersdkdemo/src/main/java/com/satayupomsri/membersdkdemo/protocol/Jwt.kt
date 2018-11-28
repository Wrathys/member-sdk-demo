package com.satayupomsri.membersdkdemo.protocol

import android.util.Base64
import com.satayupomsri.membersdkdemo.utils.AuthData
import com.satayupomsri.membersdkdemo.utils.JwtData
import com.satayupomsri.membersdkdemo.utils.MemberData
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Created by satayupomsri on 27/11/2018 AD.
 */
internal class Jwt {
    fun decoded(jwtEncoded: String): JwtData? {
        try {
            val split = jwtEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
//            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))

            val strBody = getJson(split[1])
            val objBody = JSONObject(strBody)

            val user = JSONObject(objBody["user_profile"].toString())
            val auth = JSONObject(objBody["authorized"].toString())

            return JwtData(
                    objBody["userid"].toString(),
                    MemberData(
                            user["title"].toString(),
                            user["first"].toString(),
                            user["last"].toString(),
                            user["nick"].toString(),
                            user["age"].toString(),
                            user["address"].toString(),
                            user["gender"].toString(),
                            user["phone"].toString(),
                            user["picture"].toString()
                    ),
                    AuthData(
                            auth["access_token"].toString(),
                            auth["refresh_token"].toString()
                    )
            )
        } catch (e: UnsupportedEncodingException) {
            return null
        }


    }

    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, Charset.forName("UTF-8"))
    }
}