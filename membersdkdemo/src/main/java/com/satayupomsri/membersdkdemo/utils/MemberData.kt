package com.satayupomsri.membersdkdemo.utils

import org.json.JSONObject

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
internal data class MemberData(
        private val id : Any,
        private val fullname : Any,
        private val token : Any,
        private val thumbnail : Any) {

    data class AllDataMember(
            val id : Any,
            val fullname : Any,
            val token : Any,
            val thumbnail : Any)

    fun getMemberData(): AllDataMember {
        return AllDataMember(
                id,
                fullname,
                token,
                thumbnail)
    }

    fun getJsonObject(): JSONObject {
        val json = JSONObject()

        json.put("id", id)
        json.put("fullname", fullname)
        json.put("thumbnail", thumbnail)

        return json
    }
}



