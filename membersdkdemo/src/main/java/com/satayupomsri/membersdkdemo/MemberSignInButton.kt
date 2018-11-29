package com.satayupomsri.membersdkdemo

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class MemberSignInButton : FrameLayout {

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    private var signInManager = MemberSignInManager(this.context)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.setStyle(this.context)
        this.updateTextButton(this.signInManager.isSignInSession())
        this.signInManager.setOnSignInManagerHandler { isSession -> this.updateTextButton(isSession) }
    }

    private fun setStyle(context: Context) {

        this.linearLayout = LinearLayout(context).apply {
            background = context.getDrawable(R.drawable.bt_signon)
            layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    resources.getDimension(R.dimen.bt_sign_in_height).toInt())
            gravity = Gravity.CENTER
            orientation = LinearLayout.HORIZONTAL
            minimumWidth = resources.getDimension(R.dimen.bt_sign_in_min_width).toInt()
            this@MemberSignInButton.signInManager.setCustomButton(this)
        }

        this.imageView = ImageView(context).apply {
            setImageResource(R.drawable.mn_logo)
            layoutParams = FrameLayout.LayoutParams(
                    resources.getDimension(R.dimen.bt_sign_in_size).toInt(),
                    resources.getDimension(R.dimen.bt_sign_in_size).toInt()).apply {
                setMargins(
                        resources.getDimension(R.dimen.bt_sign_in_padding_left).toInt(),
                        0,
                        resources.getDimension(R.dimen.bt_sign_in_padding_center).toInt(),
                        0)
            }
            this@MemberSignInButton.linearLayout.addView(this)
        }

        this.textView = TextView(context).apply {
            setTextColor(Color.parseColor("#ffffff"))
            setTypeface(null, Typeface.BOLD)
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                        0,
                        0,
                        resources.getDimension(R.dimen.bt_sign_in_padding_right).toInt(),
                        0)
            }
            this@MemberSignInButton.linearLayout.addView(this)
        }

        this.apply {
            addView(this.linearLayout)
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun updateTextButton(isSession: Boolean) {
        this.textView.text = if (isSession)
            context.getString(R.string.mn_sign_out)
        else
            context.getString(R.string.mn_sign_in)
    }

    fun setOnSignInListener(listener: MemberSignInListener) {
        this.signInManager.setOnSignInListener(listener)
    }
}