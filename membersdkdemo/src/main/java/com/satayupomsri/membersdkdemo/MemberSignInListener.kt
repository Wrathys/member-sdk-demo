package com.satayupomsri.membersdkdemo

import com.satayupomsri.membersdkdemo.status.MemberStatus

/**
 * Created by satayupomsri on 23/11/2018 AD.
 */
/**
 * Listener On Sign in
 */
interface MemberSignInListener {

    /**
     * @param id user id.
     * @param name user name.
     */
    fun onSignInSuccess(id: String, name: String, avatar: String)

    /**
     * @param status status code response.
     */
    fun onSignInFail(status: MemberStatus)

    /**
     * @param status status code response.
     */
    fun onSignOut(status: MemberStatus)
}
