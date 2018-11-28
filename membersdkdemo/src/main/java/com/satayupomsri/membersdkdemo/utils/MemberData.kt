package com.satayupomsri.membersdkdemo.utils

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
internal data class MemberData(
        private val title: String,
        private val first: String,
        private val last: String,
        private val nick: String,
        private val age: String,
        private val address: String,
        private val gender: String,
        private val phone: String,
        private val picture: String) {

    data class AllDataMember(
            val title: String,
            val first: String,
            val last: String,
            val nick: String,
            val age: String,
            val address: String,
            val gender: String,
            val phone: String,
            val picture: String)

    fun getMemberData(): AllDataMember {
        return AllDataMember(
                title,
                first,
                last,
                nick,
                age,
                address,
                gender,
                phone,
                picture)
    }
}