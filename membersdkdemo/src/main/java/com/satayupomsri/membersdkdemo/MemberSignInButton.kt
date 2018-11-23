package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.satayupomsri.membersdkdemo.utils.MemberData
import com.satayupomsri.membersdkdemo.utils.Prefs
import com.satayupomsri.membersdkdemo.utils.*

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class MemberSignInButton : FrameLayout, View.OnClickListener, Dialog.OnListener {

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    private var onSignInListener: MemberSignInListener? = null
    private var prefs: Prefs = Prefs(this.context)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.setStyle(this.context)

        this.updateTextButton()
    }

    private fun setStyle(context: Context) {

        this.linearLayout = LinearLayout(context)
        this.linearLayout.setBackgroundResource(R.drawable.bt_signon)
        this.linearLayout.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                resources.getDimension(R.dimen.bt_sign_in_height).toInt())
        this.linearLayout.gravity = Gravity.CENTER
        this.linearLayout.orientation = LinearLayout.HORIZONTAL
        this.linearLayout.minimumWidth = resources.getDimension(R.dimen.bt_sign_in_min_width).toInt()

        this.imageView = ImageView(context)
        this.imageView.setImageResource(R.drawable.mn_logo)
        val imageViewLayoutParams = FrameLayout.LayoutParams(
                resources.getDimension(R.dimen.bt_sign_in_size).toInt(),
                resources.getDimension(R.dimen.bt_sign_in_size).toInt())
        imageViewLayoutParams.setMargins(
                resources.getDimension(R.dimen.bt_sign_in_padding_left).toInt(),
                0,
                resources.getDimension(R.dimen.bt_sign_in_padding_center).toInt(),
                0)
        this.imageView.layoutParams = imageViewLayoutParams
        this.linearLayout.addView(this.imageView)

        this.textView = TextView(context)
        this.textView.setTextColor(Color.parseColor("#ffffff"))
        this.textView.setTypeface(null, Typeface.BOLD)
        val textViewLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textViewLayoutParams.setMargins(
                0,
                0,
                resources.getDimension(R.dimen.bt_sign_in_padding_right).toInt(),
                0)
        this.textView.layoutParams = textViewLayoutParams
        this.linearLayout.addView(this.textView)

        this.linearLayout.setOnClickListener(this)

        this.addView(this.linearLayout)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        this.layoutParams = layoutParams
    }

    private fun updateTextButton() {
        this.textView.text = if (prefs.memberData.isSignIn())
            context.getString(R.string.mn_sign_out)
        else
            context.getString(R.string.mn_sign_in)
    }

    override fun onClick(v: View) {
        if (this.prefs.memberData.isSignIn()) {
            this.signOut()
        } else {
            if (isApplicationInstalled(this.context, resources.getString(R.string.provider_package_name))) {
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
        //random for test
        val status = if (Math.random() < 0.5)
            resources.getString(R.string.member_status_sign_out_success)
        else
            resources.getString(R.string.member_status_sign_out_fail)

        this.onSignOutListener(status)
    }

    private fun updateMemberDataAndButton(id: String, name: String, avatar: String, isSignIn: Boolean) {
        // set session in app
        this.prefs.memberData = MemberData(id, name, avatar, isSignIn)
        this.updateTextButton()
    }

    private fun listenDataFromProvider() {
        // Listen to provide the data that request by sdk sign in
        val intent: Intent = (context as Activity).intent
        when {
            intent.action == Intent.ACTION_SEND -> {
                if (intent.type == resources.getString(R.string.type_text)) {
                    intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key))?.let {
                        if (intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)) == resources.getString(R.string.member_status_sign_in_success)) {
                            this.onSignInListenerSuccess(
                                    intent.getStringExtra(resources.getString(R.string.member_id_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_name_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_avatar_key)))

                            this.updateMemberDataAndButton(
                                    intent.getStringExtra(resources.getString(R.string.member_id_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_name_key)),
                                    intent.getStringExtra(resources.getString(R.string.member_avatar_key)),
                                    true)
                        } else {
                            this.onSignInListenerFail(intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)))
                        }
                    }
                }
            }
            else -> {
                if (intent.type == resources.getString(R.string.type_text)) {
                    intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key))?.let {
                        this.onSignInListenerFail(intent.getStringExtra(resources.getString(R.string.member_status_sign_in_key)))
                    }
                }
            }
        }
    }

    override fun onDone(id: String, name: String, avatar: String) {
        this.onSignInListenerSuccess(id, name, avatar)
    }

    fun setOnSignInListener(listener: MemberSignInListener) {
        this.onSignInListener = listener

        /**
         * after sign in, provider will send data back
         */
        this.listenDataFromProvider()
    }

    private fun onSignInListenerSuccess(id: String, name: String, avatar: String) {
        this.onSignInListener?.onSignInSuccess(id, name, avatar)

        this.updateMemberDataAndButton(id, name, avatar, true)
    }

    private fun onSignInListenerFail(status: String) {
        this.onSignInListener?.onSignInFail(status)
    }

    private fun onSignOutListener(status: String) {
        this.onSignInListener?.onSignOut(status)

        if (status == resources.getString(R.string.member_status_sign_out_success))
            this.updateMemberDataAndButton("", "", "", false)
    }

}