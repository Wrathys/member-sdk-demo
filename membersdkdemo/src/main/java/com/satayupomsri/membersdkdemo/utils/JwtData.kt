package com.satayupomsri.membersdkdemo.utils

/**
 * Created by satayupomsri on 27/11/2018 AD.
 */
internal data class JwtData (
        private val id: String,
        private val memberData: MemberData,
        private val authData: AuthData) {

    data class AllJwtData(
            val id: String,
            val memberData: MemberData,
            val authData: AuthData)

    fun getJwtData(): AllJwtData {
        return AllJwtData(id, memberData, authData)
    }
}