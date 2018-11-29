package com.satayupomsri.membersdkdemo.utils

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
internal data class MemberData(
        private val id : String,
        private val fullname : String,
        private val token : String,
        private val thumbnail : String) {

    data class AllDataMember(
            val id : String,
            val fullname : String,
            val token : String,
            val thumbnail : String)

    fun getMemberData(): AllDataMember {
        return AllDataMember(
                id,
                fullname,
                token,
                thumbnail)
    }
}



