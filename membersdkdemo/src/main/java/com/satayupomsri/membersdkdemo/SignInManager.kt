package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import com.satayupomsri.membersdkdemo.protocol.Jwt
import com.satayupomsri.membersdkdemo.status.MemberStatus
import com.satayupomsri.membersdkdemo.utils.Prefs

/**
 * Created by satayupomsri on 28/11/2018 AD.
 */
internal class SignInManager(private var context: Context) : Dialog.WebViewOnLoadFinish {

    private lateinit var signInManagerHandler: SignInManagerHandler
    private var onSignInListener: MemberSignInListener? = null
    private var prefs: Prefs = Prefs(this.context)

    override fun onDone(jwt: String?) {
        jwt?.let {
            val decode = Jwt().decoded(jwt)
            decode?.let {
                val (id, memberData) = decode.getJwtData()
                val profile = memberData.getMemberData()

                this.prefs.jwt = jwt
                this.signInManagerHandler.onUpdateButton(true)
                this.onSignInListenerSuccess(id, "${profile.first} ${profile.last}", profile.picture)
                return
            }
        }

        this.onSignInListenerFail(MemberStatus.SIGN_IN_STATUS_FAIL)
    }

    fun setOnSignInManagerHandler(listener: SignInManagerHandler) {
        this.signInManagerHandler = listener
    }

    fun setOnDataUpdate(listener: MemberSignInListener) {
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
            this.prefs.jwt = null
            this.signInManagerHandler.onUpdateButton(false)
        }
    }

    private fun signInWithSdk() {
        val dialog = Dialog()
        val activity = this.context as Activity
        dialog.show(this, activity.fragmentManager, "fragment")
    }

    private fun signOut() {
        //random for test
        val status = if (Math.random() < 0.5)
            MemberStatus.SIGN_OUT_STATUS_SUCCESS
        else
            MemberStatus.SIGN_OUT_STATUS_FAIL

        this.onSignOutListener(status)
    }

    fun isSignInSession(): Boolean {
        return this.prefs.jwt != null
    }

    fun onClickListener() {
        if(isSignInSession()) {
            this.signOut()
        } else {
            this.signInWithSdk()
        }
    }

    interface SignInManagerHandler {

        /**
         * When sign in/out changed
         */
        fun onUpdateButton(isSession: Boolean)
    }
}