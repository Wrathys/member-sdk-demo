package com.satayupomsri.memberdemo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.satayupomsri.memberdemo.signinsdk.ListenerSignInSdk
import com.satayupomsri.memberdemo.signinsdk.utils.Prefs
//import com.satayupomsri.membersdkdemo.utils.MemberData
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.net.URI

class SignInActivity : AppCompatActivity() {

    private var id: String? = null
    private var name: String? = null
    private var avatar: String? = null
    private var isSignIn: Boolean = false
    private var consumerPackageName: String? = null

    companion object {
        private val BASE_URL = "http://www.google.co.th"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

//        if (intent?.action == Intent.ACTION_SEND) {
//            intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key))?.let {
//                intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))?.let {
//                    if (intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key)) == this.packageName) {
//                        consumerPackageName = intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))
//                    }
//                }
//            }
//        }
//
//        wv_sign_in.webViewClient = object : WebViewClient() {
//            override fun onLoadResource(view: WebView?, url: String?) {
//                super.onLoadResource(view, url)
//
//                signInAction(url)
//                getHash(url)
//                getAvatar(url)
//
//                returnData()
//            }
//        }
//        wv_sign_in.loadUrl(BASE_URL)
    }

    private fun returnData() {
//        id?.let {
//            name?.let {
//                avatar?.let {
//                    if(isSignIn) {
//                        val pref = Prefs(this)
//                        pref.memberData = MemberData(id ?: "", name ?: "", avatar ?: "", true)
//
//                        if (!consumerPackageName.isNullOrEmpty()) {
//                            ListenerSignInSdk(this).provideData(consumerPackageName!!, id ?: "", name ?: "", avatar ?: "")
//                        } else {
//                            Toast.makeText(applicationContext, resources.getString(R.string.welcome_sign_in, id ?: ""), Toast.LENGTH_SHORT).show()
//                            val returnIntent = Intent()
//                            returnIntent.putExtra(getString(R.string.member_id_key), id?:"no hash")
//                            returnIntent.putExtra(getString(R.string.member_name_key), name?:"no link")
//                            returnIntent.putExtra(getString(R.string.member_avatar_key), avatar?:"no avatar")
//                            setResult(Activity.RESULT_OK, returnIntent)
//                            finish()
//                        }
//                        isSignIn = false
//                    }
//                }
//            }
//        }
    }

    private fun signInAction(url: String?) {
        val uri = URI.create(url)
        uri.scheme?.let {
            if (uri.scheme.matches("^(http|https)".toRegex())) {
                uri.host?.let {
                    if (uri.host == "accounts.google.com") {
                        uri.path?.let {
                            if (uri.path == "/ServiceLogin") {
                                isSignIn = true
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
                avatar = url
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
                                id = path[1]
                            }
                            if (!path[2].isEmpty() && !path[3].isEmpty()) {
                                name = "${path[2]} ${path[3]}"
                            }
                        }
                    }
                }
            }
        }
    }
}
