package com.satayupomsri.membersdkdemo.utils

/**
 * Created by satayupomsri on 27/11/2018 AD.
 */
internal data class AuthData (
        private val access_token: String,
        private val refresh_token: String) {

    data class AllAuth(
            val access_token: String,
            val refresh_token: String)

    fun getAuthData(): AllAuth {
        return AllAuth(access_token, refresh_token)
    }
}