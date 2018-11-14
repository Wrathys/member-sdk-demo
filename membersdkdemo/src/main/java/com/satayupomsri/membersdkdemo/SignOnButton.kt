package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.satayupomsri.membersdkdemo.R.id.bt_sign_in_fail
import com.satayupomsri.membersdkdemo.R.id.bt_sign_in_success

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class SignOnButton : FrameLayout, View.OnClickListener, Dialog.OnListener {

    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var linearLayout: LinearLayout? = null
    private var onSignInListener: OnSignInListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.setStyle(context)
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
        this.textView!!.text = context.getString(R.string.mn_sign_in)
        this.textView!!.setTextColor(Color.parseColor("#ffffff"))
        this.textView!!.setTypeface(null, Typeface.BOLD)
        this.linearLayout!!.addView(this.textView)

        this.linearLayout!!.setOnClickListener(this)

        this.addView(this.linearLayout)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        this.layoutParams = layoutParams
    }

    override fun onClick(v: View) {
        val dialog = Dialog()
        val activity = context as Activity
        dialog.show(this, activity.fragmentManager, "my_fragment")
    }

    override fun onSuccess(id: String, name: String) {
        onSigninListenerSuccess(id, name)
    }

    override fun onFail(status: Int) {
        onSigninListenerFail(status)
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
        fun onSignInFail(status: Int)
    }

    fun setOnSigninListener(listener: OnSignInListener) {
        this.onSignInListener = listener
    }

    private fun onSigninListenerSuccess(id: String, name: String) {
        this.onSignInListener!!.onSignInSuccess(id, name)
    }

    private fun onSigninListenerFail(status: Int) {
        this.onSignInListener!!.onSignInFail(status)
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

}