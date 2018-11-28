package com.satayupomsri.membersdkdemo

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class MemberSignInButton : FrameLayout, View.OnClickListener, SignInManager.SignInManagerHandler {

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    private var signInManager = SignInManager(this.context)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.signInManager.setOnSignInManagerHandler(this)
        this.setStyle(this.context)
        this.updateTextButton(this.signInManager.isSignInSession())
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

    private fun updateTextButton(isSession: Boolean) {
        this.textView.text = if (isSession)
            context.getString(R.string.mn_sign_out)
        else
            context.getString(R.string.mn_sign_in)
    }

    override fun onClick(v: View) {
        this.signInManager.onClickListener()
    }

    override fun onUpdateButton(isSession: Boolean) {
        this.updateTextButton(isSession)
    }

    fun setOnSignInListener(listener: MemberSignInListener) {
        this.signInManager.setOnDataUpdate(listener)
    }
}