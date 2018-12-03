package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.app.AlertDialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import android.widget.RelativeLayout
import com.satayupomsri.membersdkdemo.protocol.ServerProtocol
import com.satayupomsri.membersdkdemo.status.MemberStatus
import com.satayupomsri.membersdkdemo.utils.*
import kotlinx.android.synthetic.main.dialog.view.*

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
internal class Dialog : DialogFragment() {

    private lateinit var container: View
    private lateinit var validSignInAction: ValidateSignInAction
    private var provideDataHandlerSuccess: ((jwt: String?) -> Unit?)? = null
    private var provideDataHandlerFail: ((status: MemberStatus) -> Unit?)? = null
    private var isFirstLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //clear cookie about data sign in
        CookieManager.getInstance().removeAllCookies { }

        validSignInAction = ValidateSignInAction(this.context)
        container = activity.layoutInflater.inflate(R.layout.dialog, null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val alert = AlertDialog.Builder(activity)

        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        //set init height
        this.container.wv_container.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size.y)

        this.container.wv_sign_in.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                this@Dialog.container.pb_container.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                this@Dialog.apply {
                    this.container.pb_container.visibility = View.GONE
                    this.validSignInAction.onSignIn(
                            url,
                            { jwt -> this.onSignInSuccess(jwt) },
                            { status -> this.onSignInFail(status)}
                    )

                    if (this.isFirstLoaded) {
                        this.isFirstLoaded = false
                        this.container.wv_sign_in.visibility = View.VISIBLE
                        //set height follow web site
                        this.container.wv_container.layoutParams =
                                RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT)
                    }
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                this@Dialog.onSignInFail(MemberStatus.LOAD_PAGE_SIGN_IN_FAIL)
            }
        }
        this.container.
                wv_sign_in.
                loadUrl(getKey(this.context, ServerProtocol.serverProtocolName, ServerProtocol.SIGN_IN_API))

        alert.setView(this.container)

        //set resize webview when keyboard show
        val alertCreate = alert.create()
        alertCreate.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return alertCreate
    }

    fun show(
            manager: FragmentManager?, tag: String?,
            handlerSuccess: (jwt: String?) -> Unit,
            handlerFail: (status: MemberStatus) -> Unit) {

        this.isFirstLoaded = true
        this.provideDataHandlerSuccess = handlerSuccess
        this.provideDataHandlerFail = handlerFail

        this.show(manager, tag)
    }

    private fun onSignInSuccess(jwt: String?) {
        this.provideDataHandlerSuccess?.invoke(jwt)
        this.dismiss()
    }

    private fun onSignInFail(status: MemberStatus) {
        this.provideDataHandlerFail?.invoke(status)
        this.dismiss()
    }
}