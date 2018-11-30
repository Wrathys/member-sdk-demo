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
import java.net.URI

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
internal class Dialog : DialogFragment() {

    private lateinit var container: View
    private var provideDataHandlerSuccess: ((jwt: String?) -> Unit?)? = null
    private var provideDataHandlerFail: ((status: MemberStatus) -> Unit?)? = null
    private var isFirstLoaded = true

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        container = activity.layoutInflater.inflate(R.layout.dialog, null)

        val alert = AlertDialog.Builder(activity)

        //clear cookie about data sign in
        CookieManager.getInstance().removeAllCookies { }

        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        //set init height
        container.wv_container.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size.y)

        container.wv_sign_in.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                container.pb_container.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                container.pb_container.visibility = View.GONE
                this@Dialog.onSignIn(url)

                if(isFirstLoaded) {
                    isFirstLoaded = false
                    container.wv_sign_in.visibility = View.VISIBLE
                    //set height follow web site
                    container.wv_container.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                this@Dialog.onSignInFail(MemberStatus.LOAD_PAGE_SIGN_IN_FAIL)
            }
        }
        container.wv_sign_in.loadUrl(getKey(this.context, ServerProtocol.serverProtocolName, ServerProtocol.SIGN_IN_API))

        alert.setView(container)

        //set resize webview when keyboard show
        val alertCreate = alert.create()
        alertCreate.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return alertCreate
    }

    fun show(manager: FragmentManager?, tag: String?, handlerSuccess: (jwt: String?) -> Unit, handlerFail: (status: MemberStatus) -> Unit) {
        this.isFirstLoaded = true
        this.provideDataHandlerSuccess = handlerSuccess
        this.provideDataHandlerFail = handlerFail

        this.show(manager, tag)
    }

    private fun onSignIn(url: String?) {
        url?.let {
            val uri = URI.create(url)
            uri.scheme?.let {
                if (uri.scheme.matches("^(http|https)".toRegex())) {
                    uri.host?.let {
                        if (uri.host == getKey(this.context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_VALIDATE_HOST)) {
                            uri.path?.let {
                                if (uri.path == getKey(this.context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_VALIDATE_PATH)) {
                                    uri.query?.let {
                                        val arrData = uri.query.split(getKey(this.context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_SPLIT_DATA))
                                        if (arrData[0] == getKey(this.context, ServerProtocol.validateSignInName, ServerProtocol.SIGN_IN_VALIDATE_QUERY)) {
                                            if(arrData.size < 2) {
                                                this.onSignInFail(MemberStatus.SIGN_IN_RESPONSE_EMPTY)
                                            } else {
                                                if(arrData[1].isEmpty()){
                                                    this.onSignInFail(MemberStatus.SIGN_IN_RESPONSE_EMPTY)
                                                } else {
                                                    this.onSignInSuccess(arrData[1])
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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