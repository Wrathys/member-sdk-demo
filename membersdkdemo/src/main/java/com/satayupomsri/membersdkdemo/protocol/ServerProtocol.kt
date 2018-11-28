package com.satayupomsri.membersdkdemo.protocol

/**
 * Created by satayupomsri on 27/11/2018 AD.
 */
internal object ServerProtocol {
    //extension
    val ext = "txt"

    //protocol source name
    val serverProtocolName = "server_protocol"
    val validateSignInName = "validate_sign_in"
    val prefName = "preference_jwt"

    //api
    val SIGN_IN_API_ID = 0x000000

    //validate sign in
    val SIGN_IN_VALIDATE_HOST_ID = 0x000000
    val SIGN_IN_VALIDATE_PATH_ID = 0x000001
    val SIGN_IN_VALIDATE_QUERY_ID = 0x000002
    val SIGN_IN_SPLIT_DATA_ID = 0x000003

    //preference
    val PREF_NAME_ID = 0x000000
    val PREF_FIELD_ID = 0x000001
}