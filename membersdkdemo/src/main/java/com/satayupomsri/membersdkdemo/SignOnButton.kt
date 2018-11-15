package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.content.pm.PackageManager
import com.satayupomsri.membersdkdemo.utils.MemberData
import com.satayupomsri.membersdkdemo.utils.Prefs

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class SignOnButton : FrameLayout, View.OnClickListener, Dialog.OnListener {

    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var linearLayout: LinearLayout? = null
    private var onSignInListener: OnSignInListener? = null
    private var prefs: Prefs? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.prefs = Prefs(this.context)
        this.setStyle(this.context)
    }

    private fun setStyle(context: Context) {

        this.linearLayout = LinearLayout(context)
        this.linearLayout!!.setBackgroundResource(R.drawable.bt_signon)
        this.linearLayout!!.layoutParams = ViewGroup.LayoutParams(dp(128), dp(54))
        this.linearLayout!!.gravity = Gravity.CENTER
        this.linearLayout!!.orientation = LinearLayout.HORIZONTAL

        this.imageView = ImageView(context)
        this.imageView!!.setImageResource(R.drawable.mn_logo)
        val lp = FrameLayout.LayoutParams(dp(24), dp(24))
        lp.setMargins(0, 0, dp(16), 0)
        this.imageView!!.layoutParams = lp
        this.linearLayout!!.addView(this.imageView)

        this.textView = TextView(context)
        this.textView!!.text = if(prefs!!.memberData.isSignIn())
            context.getString(R.string.mn_sign_out)
        else
            context.getString(R.string.mn_sign_in)
        this.textView!!.setTextColor(Color.parseColor("#ffffff"))
        this.textView!!.setTypeface(null, Typeface.BOLD)
        this.linearLayout!!.addView(this.textView)

        this.linearLayout!!.setOnClickListener(this)

        this.addView(this.linearLayout)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        this.layoutParams = layoutParams
    }

    override fun onClick(v: View) {
        if(this.prefs!!.memberData.isSignIn()) {
            this.signOut()
        } else {
            if(isApplicationInstalled(resources.getString(R.string.provider_package_name))){
                this.signInWithApplication()
            } else {
                this.signInWithSdk()
            }
        }
    }

    private fun signInWithApplication() {
        val context2 = this.context
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(resources.getString(R.string.provider_package_key), resources.getString(R.string.provider_package_name))
            putExtra(resources.getString(R.string.consumer_package_key), context2.packageName)
            type = resources.getString(R.string.type_text)
        }
        sendIntent.`package` = resources.getString(R.string.provider_package_name)
        this.context.startActivity(sendIntent)
       (this.context as Activity).finish()
    }

    private fun signInWithSdk() {
        val dialog = Dialog()
        val activity = this.context as Activity
        dialog.show(this, activity.fragmentManager, "fragment")
    }

    private fun signOut() {
        val status: String

        // sign out may success or fail, random for demo
        if(Math.random() > 0.9) {
            status = resources.getString(R.string.member_status_sign_out_success)
            this.textView!!.text = resources.getString(R.string.mn_sign_in)

            // clear session member data in app
            this.prefs!!.memberData = MemberData("", "", false)
        } else {
            status = resources.getString(R.string.member_status_sign_out_fail)
            this.textView!!.text = resources.getString(R.string.mn_sign_out)
        }

        this.onSignOutListener(status)
    }

    private fun listenDataFromProvider() {
        // Listen to provide the data that request by sdk sign in
        val intent: Intent = (context as Activity).intent
        when {
            intent.action == Intent.ACTION_SEND -> {
                if (intent.type == resources.getString(R.string.type_text)) {
                    intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key))?.let {
                        if (intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)) == resources.getString(R.string.member_status_sign_in_success)) {
                            onSigninListenerSuccess(
                                    intent.getStringExtra(resources.getString(R.string.member_id_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_name_key))
                            )

                            // set session in app
                            prefs!!.memberData = MemberData(
                                    intent.getStringExtra(resources.getString(R.string.member_id_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_name_key)),
                                    true)
                        } else {
                            onSigninListenerFail(intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)))
                        }
                    }
                }
            }
            else -> {
                if (intent.type == resources.getString(R.string.type_text)) {
                    intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key))?.let {
                        onSigninListenerFail(intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)))
                    }
                }
            }
        }
    }

    override fun onSuccess(id: String, name: String) {
        this.onSigninListenerSuccess(id, name)
    }

    override fun onFail(status: String) {
        this.onSigninListenerFail(status)
    }

    /**
     * Listener On Sign in
     */
    interface OnSignInListener {

        /**
         * @param id user id.
         * @param name user name.
         */
        fun onSignInSuccess(id: String, name: String)

        /**
         * @param status status code response.
         */
        fun onSignInFail(status: String)

        /**
         * @param status status code response.
         */
        fun onSignOut(status: String)
    }

    fun setOnSigninListener(listener: OnSignInListener) {
        this.onSignInListener = listener
        this.listenDataFromProvider()
    }

    private fun onSigninListenerSuccess(id: String, name: String) {
        this.onSignInListener!!.onSignInSuccess(id, name)
    }

    private fun onSigninListenerFail(status: String) {
        this.onSignInListener!!.onSignInFail(status)
    }

    private fun onSignOutListener(status: String) {
        this.onSignInListener!!.onSignOut(status)
    }

    /**
     * @param dp number of design pixels.
     * @return number of pixels corresponding to the desired design pixels.
     */
    private fun dp(dp: Int): Int {
        val metrics = Resources.getSystem().getDisplayMetrics()
        val dpMultiplier = metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT

        return dp * dpMultiplier
    }

    private fun isApplicationInstalled(uri: String): Boolean {
        val pm = this.context.packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {}

        return false
    }

}