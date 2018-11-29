package com.satayupomsri.membersdkdemo.protocol

import android.content.Context
import android.util.Base64
import android.util.Log
import com.satayupomsri.membersdkdemo.utils.MemberData
import com.satayupomsri.membersdkdemo.utils.getKey
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Created by satayupomsri on 27/11/2018 AD.
 */
internal class Jwt(context: Context) {

    private var ID_KEY = getKey(context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_KEY_ID)
    private var FULL_NAME_KEY = getKey(context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_KEY_NAME)
    private var TOKEN_KEY = getKey(context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_KEY_TOKEN)
    private var THUMBNAIL_KEY = getKey(context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_KEY_THUMBNAIL)
    private var DATA_KEY = getKey(context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_KEY_DATA_OBJECT)

    fun decoded(jwtEncoded: String): MemberData? {
        try {
            val split = jwtEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
//            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))

            if(split.size < 2) return null

            val strBody = getJson(split[1])
            val objBody: JSONObject? = JSONObject(strBody)
            val data: JSONObject? = objBody?.getJSONObject(DATA_KEY)

            data?.let {
                if (!data.isNull(ID_KEY) && !data.isNull(TOKEN_KEY)) {
                    val id = data[ID_KEY]
                    val fullname = if(data.isNull(FULL_NAME_KEY)) "" else data[FULL_NAME_KEY]
                    val token = data[TOKEN_KEY]
                    val thumb = if(data.isNull(THUMBNAIL_KEY)) "" else data[THUMBNAIL_KEY]

                    return MemberData(
                            id.toString(),
                            fullname.toString(),
                            token.toString(),
                            thumb.toString()
                    )
                }
            }
            return null
        } catch (e: UnsupportedEncodingException) {
            return null
        } catch (e: Throwable) {
            return null
        }
    }

    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, Charset.forName("UTF-8"))
    }
}