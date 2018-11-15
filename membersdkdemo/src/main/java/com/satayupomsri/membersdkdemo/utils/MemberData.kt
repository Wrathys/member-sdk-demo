package com.satayupomsri.membersdkdemo.utils

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
data class MemberData(private val id: String, private val name: String, private val isSignIn: Boolean) {
    data class AllDataMember(val id: String, val name: String, val isSignIn: Boolean)

    fun getMemberData(): AllDataMember {
        return AllDataMember(id ,name, isSignIn)
    }

    fun isSignIn(): Boolean {
        return isSignIn
    }
}