package com.satayupomsri.membersdkdemo

import com.satayupomsri.membersdkdemo.status.MemberStatus
import org.json.JSONObject

/**
 * Created by satayupomsri on 23/11/2018 AD.
 */
/**
 * Listener On Sign in
 */
interface MemberSignInListener {

    /**
     * @param json data from sign in
     */
    fun onSignInSuccess(json: JSONObject)

    /**
     * @param status status code response.
     */
    fun onSignInFail(status: MemberStatus)

    fun onSignOutSuccess()

    fun onSignOutFail()
}
