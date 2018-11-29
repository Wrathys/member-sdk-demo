package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import android.view.View
import com.satayupomsri.membersdkdemo.protocol.Jwt
import com.satayupomsri.membersdkdemo.status.MemberStatus
import com.satayupomsri.membersdkdemo.utils.Prefs

/**
 * Created by satayupomsri on 28/11/2018 AD.
 */
class MemberSignInManager(private var context: Context) {

    private var updateButtonHandler: ((isSession: Boolean) -> Unit?)? = null
    private var onSignInListener: MemberSignInListener? = null
    private var prefs: Prefs = Prefs(this.context)

    private fun signInWithSdk() {
        Dialog().show((this.context as Activity).fragmentManager, "fragment") { jwt -> onSignInDone(jwt) }
    }

    private fun signOut() {
        //random for test
        val status = if (Math.random() < 0.7)
            MemberStatus.SIGN_OUT_STATUS_SUCCESS
        else
            MemberStatus.SIGN_OUT_STATUS_FAIL

        this.onSignOutListener(status)
    }

    internal fun isSignInSession(): Boolean {
        return this.prefs.storeJwt != null
    }

    private fun signInSignOut() {
        if(isSignInSession()) {
            this.signOut()
        } else {
            this.signInWithSdk()
        }
    }

    internal fun setOnSignInManagerHandler(handler: (isSession: Boolean) -> Unit) {
        this.updateButtonHandler = handler
    }

    fun setCustomButton(view: View?) {
        view?.setOnClickListener { _ -> this.signInSignOut() }
    }

    fun setOnSignInListener(listener: com.satayupomsri.membersdkdemo.MemberSignInListener) {
        this.onSignInListener = listener
    }

    private fun onSignInListenerSuccess(id: String, name: String, avatar: String) {
        this.onSignInListener?.onSignInSuccess(id, name, avatar)
    }

    private fun onSignInListenerFail(status: MemberStatus) {
        this.onSignInListener?.onSignInFail(status)
    }

    private fun onSignOutListener(status: MemberStatus) {
        this.onSignInListener?.onSignOut(status)

        if (status == MemberStatus.SIGN_OUT_STATUS_SUCCESS) {
            this.prefs.storeJwt = null
            this.updateButtonHandler?.invoke(false)
        }
    }

    private fun onSignInDone(jwt: String?) {
        jwt?.let {
            val decode = Jwt(context).decoded(jwt)
            decode?.let {
                val (id, fullname, _, thumbnail) = decode.getMemberData()

                this.prefs.storeJwt = jwt
                this.updateButtonHandler?.invoke(true)
                this.onSignInListenerSuccess(id, fullname, thumbnail)
                return
            }
        }

        this.onSignInListenerFail(MemberStatus.SIGN_IN_STATUS_FAIL)
    }
}