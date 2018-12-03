package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.view.View
import com.satayupomsri.membersdkdemo.protocol.Jwt
import com.satayupomsri.membersdkdemo.status.MemberStatus
import com.satayupomsri.membersdkdemo.utils.Prefs
import com.satayupomsri.membersdkdemo.utils.isNetworkConnected
import org.json.JSONObject

/**
 * Created by satayupomsri on 28/11/2018 AD.
 */
internal class MemberSignInManager(private var context: Context) : BroadcastReceiver() {

    private var updateButtonHandler: ((isSession: Boolean) -> Unit?)? = null
    private var onSignInListener: MemberSignInListener? = null
    private var prefs: Prefs = Prefs(this.context)
    private var dialogSignIn: Dialog = Dialog().apply {
        isCancelable = true
    }

    init {
        context.registerReceiver(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun signInWithSdk() {
        dialogSignIn.show(
                (this.context as Activity).fragmentManager,
                "fragment",
                { jwt -> onSignInDone(jwt) },
                { status -> onSignInListenerFail(status) }
        )
    }

    private fun signOut() {
        this.onSignOutListener()
    }

    fun isSignInSession(): Boolean {
        return this.prefs.storeJwt != null
    }

    /**
     * cannot sign in without internet
     * but can be sign out
     */
    fun signInSignOut() {
        when {
            isSignInSession() -> this.signOut()
            isNetworkConnected(context) -> this.signInWithSdk()
            else -> this.onSignInListener?.onSignInFail(MemberStatus.NO_INTERNET_CONNECTION)
        }
    }

    fun setOnSignInManagerHandler(handler: (isSession: Boolean) -> Unit) {
        this.updateButtonHandler = handler
    }

    fun setCustomButton(view: View?) {
        view?.setOnClickListener { _ -> this.signInSignOut() }
    }

    fun setOnSignInListener(listener: com.satayupomsri.membersdkdemo.MemberSignInListener) {
        this.onSignInListener = listener
    }

    private fun onSignInListenerSuccess(json: JSONObject) {
        this.onSignInListener?.onSignInSuccess(json)
    }

    private fun onSignInListenerFail(status: MemberStatus) {
        this.onSignInListener?.onSignInFail(status)
    }

    private fun onSignOutListener() {
        this.onSignInListener?.onSignOutSuccess()

        this.prefs.storeJwt = null
        this.updateButtonHandler?.invoke(false)
    }

    private fun onSignInDone(jwt: String?) {
        jwt?.let {
            val decode = Jwt().decoded(jwt)
            decode?.let {
                this.prefs.storeJwt = jwt
                this.updateButtonHandler?.invoke(true)
                this.onSignInListenerSuccess(decode.getJsonObject())
                return
            }
        }

        this.onSignInListenerFail(MemberStatus.SIGN_IN_RESPONSE_EMPTY)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.extras != null) {
            val connectivityManager: ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = connectivityManager?.activeNetworkInfo

            if (ni != null && ni.isConnectedOrConnecting) {
                //connected
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
                this@MemberSignInManager.apply {
                    this.dialogSignIn.dialog.also {
                        if (it != null && it.isShowing) {
                            this.dialogSignIn.dismiss()
                            this.onSignInListener?.onSignInFail(MemberStatus.NO_INTERNET_CONNECTION)
                        }
                    }
                }
            }
        }
    }
}