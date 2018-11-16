package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.app.AlertDialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.net.URI

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class Dialog : DialogFragment() {

    private var id: String? = null
    private var name: String? = null
    private var avatar: String? = null
    private var isSignIn: Boolean = false
    private var listener: OnListener? = null

    private companion object {
        private val BASE_URL = "http://www.google.co.th"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val view: View = activity.layoutInflater.inflate(R.layout.dialog, null)
        val alert = AlertDialog.Builder(activity)

        view.wv_sign_in.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)

                this@Dialog.signInAction(url)
                this@Dialog.getHash(url)
                this@Dialog.getAvatar(url)

                this@Dialog.onDone()
            }
        }
        view.wv_sign_in.loadUrl(BASE_URL)


        alert.setView(view)

        return alert.create()
    }

    private fun onDone() {
        id?.let {
            name?.let {
                avatar?.let {
                    if (isSignIn) {
                        this@Dialog.isSignIn = false
                        this@Dialog.listener!!.onDone(id ?: "", name ?: "", avatar ?: "")
                        this@Dialog.dismiss()
                    }
                }
            }
        }
    }

    private fun signInAction(url: String?) {
        val uri = URI.create(url)
        uri.scheme?.let {
            if (uri.scheme.matches("^(http|https)".toRegex())) {
                uri.host?.let {
                    if (uri.host == "accounts.google.com") {
                        uri.path?.let {
                            if (uri.path == "/ServiceLogin") {
                                this@Dialog.isSignIn = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAvatar(url: String?) {
        url?.let {
            if (url.matches("^(http|https)://lh3\\.googleusercontent\\.com/(\\S+)(/photo\\.jpg)\\b".toRegex())) {
                this@Dialog.avatar = url
            }
        }
    }

    private fun getHash(url: String?) {
        val uri = URI.create(url)
        uri.scheme?.let {
            if (uri.scheme.matches("^(http|https)".toRegex())) {
                uri.host?.let {
                    if (uri.host == "lh3.googleusercontent.com") {
                        uri.path?.let {
                            val path = uri.path.split("/")
                            if (!path[1].isEmpty()) {
                                this@Dialog.id = path[1]
                            }
                            if (!path[2].isEmpty() && !path[3].isEmpty()) {
                                this@Dialog.name = "${path[2]} ${path[3]}"
                            }
                        }
                    }
                }
            }
        }
    }

    fun show(listener: OnListener, manager: FragmentManager?, tag: String?) {
        this@Dialog.listener = listener
        this@Dialog.show(manager, tag)
    }

    interface OnListener {

        /**
         * @param id user id.
         * @param name user name.
         * @param avatar user avatar.
         */
        fun onDone(id: String, name: String, avatar: String)
    }
}