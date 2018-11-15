package com.satayupomsri.membersdkdemo

import android.app.AlertDialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.satayupomsri.membersdkdemo.R.id.bt_sign_in_fail
import com.satayupomsri.membersdkdemo.R.id.bt_sign_in_success

/**
 * Created by satayupomsri on 14/11/2018 AD.
 */
class Dialog : DialogFragment(), View.OnClickListener {

    var listener: OnListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val view: View = activity.layoutInflater.inflate(R.layout.dialog, null)
        val alert = AlertDialog.Builder(activity)

        view.findViewById<TextView>(R.id.bt_sign_in_success).setOnClickListener(this)
        view.findViewById<TextView>(R.id.bt_sign_in_fail).setOnClickListener(this)

        alert.setView(view)

        return alert.create()
    }

    override fun onClick(v: View?) {
            when (v?.id) {
                bt_sign_in_success -> {
                    val v1 = "m00002"
                    val v2 = "User2 General2"
                    this.listener!!.onSuccess(v1, v2)
                    this.dismiss()
                }
                bt_sign_in_fail -> {
                    this.listener!!.onFail(resources.getString(R.string.member_status_sign_in_fail))
                    this.dismiss()
                }
                else -> {
                    // Note the block
                }
            }
    }

    fun show(listener: OnListener, manager: FragmentManager?, tag: String?) {
        this.listener = listener
        show(manager, tag)
    }

    interface OnListener {

        /**
         * @param id user id.
         * @param name user name.
         */
        fun onSuccess(id: String, name: String)

        /**
         * @param status status code response.
         */
        fun onFail(status: String)
    }
}