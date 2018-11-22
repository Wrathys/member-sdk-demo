package com.satayupomsri.membersdkdemo

/**
 * Created by satayupomsri on 22/11/2018 AD.
 */
interface OnSignInListener {

    /**
     * Callback when sing in success
     *
     * @param id user id.
     * @param name user name.
     */
    fun onSignInSuccess(id: String, name: String, avatar: String)

    /**
     * Callback when sing in fail
     *
     * @param status status code response.
     */
    fun onSignInFail(status: String)

    /**
     * Callback when sing out
     *
     * @param status status code response.
     */
    fun onSignOut(status: String)
}