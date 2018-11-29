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
    val SIGN_IN_API = 0x000000

    //validate sign in
    val SIGN_IN_VALIDATE_HOST = 0x000000
    val SIGN_IN_VALIDATE_PATH = 0x000001
    val SIGN_IN_VALIDATE_QUERY = 0x000002
    val SIGN_IN_SPLIT_DATA = 0x000003

    //preference
    val PREF_NAME = 0x000000
    val PREF_FIELD = 0x000001

    //key data from jwt
    val SIGN_IN_KEY_ID = 0x000004
    val SIGN_IN_KEY_NAME = 0x000005
    val SIGN_IN_KEY_TOKEN = 0x000006
    val SIGN_IN_KEY_THUMBNAIL = 0x000007
    val SIGN_IN_KEY_DATA_OBJECT = 0x000008
}