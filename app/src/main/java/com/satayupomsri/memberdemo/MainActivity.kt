package com.satayupomsri.memberdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.satayupomsri.memberdemo.signinsdk.ListenerSignInSdk
import com.satayupomsri.memberdemo.signinsdk.MemberData
import com.satayupomsri.memberdemo.signinsdk.utils.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var pref: Prefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listen to provide the data that request by sdk sign in
        ListenerSignInSdk(this)

        pref = Prefs(this)

        if (pref!!.memberData.isSignIn()) {
            val (id, name) = pref!!.memberData.getMemberData()
            tv_member_id.text = id
            tv_member_name.text = name
            tv_already_account.visibility = View.INVISIBLE
        } else {
            tv_member_id.text = ""
            tv_member_name.text = ""
            tv_already_account.visibility = View.VISIBLE
        }

        initButtonSignSignOut()
    }

    private fun initButtonSignSignOut() {
        bt_go_sign_in.apply {
            text = if (pref!!.memberData.isSignIn())
                resources.getString(R.string.bt_sign_out)
            else
                resources.getString(R.string.bt_sign_in)

            setOnClickListener { _ ->
                run {
                    if (pref!!.memberData.isSignIn()) {
                        pref!!.memberData = MemberData("", "", false)

                        tv_member_id.text = ""
                        tv_member_name.text = ""
                        tv_already_account.visibility = View.VISIBLE
                        this.text = resources.getString(R.string.bt_sign_in)
                    } else {
                        context.startActivity(Intent(context, SignInActivity::class.java))
                    }
                }
            }
        }

    }

}
