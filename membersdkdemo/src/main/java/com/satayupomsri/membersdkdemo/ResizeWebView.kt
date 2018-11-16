package com.satayupomsri.membersdkdemo

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView

/**
 * Created by satayupomsri on 16/11/2018 AD.
 */
class ResizeWebView : WebView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, getHeight(context))
    }

    private fun getHeight(context: Context): Int {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }
}