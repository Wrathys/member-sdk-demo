package com.satayupomsri.memberdemo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.satayupomsri.memberdemo.signinsdk.ListenerSignInSdk
import com.satayupomsri.memberdemo.signinsdk.utils.Prefs
import com.satayupomsri.membersdkdemo.utils.MemberData
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
            val (id, name, avatar) = pref!!.memberData.getMemberData()
            tv_member_id.text = id
            tv_member_name.text = name
            Glide.with(this)
                    .load(avatar)
                    .into(iv_member_avatar)
            tv_already_account.visibility = View.INVISIBLE
        } else {
            setDefaultUi()
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
                        pref!!.memberData = MemberData("", "", "", false)
                        setDefaultUi()
                    } else {
                        this@MainActivity.startActivityForResult(Intent(context, SignInActivity::class.java), 1)
                    }
                }
            }
        }
    }

    private fun setDefaultUi() {
        tv_member_id.text = ""
        tv_member_name.text = ""
        tv_member_status.text = ""
        iv_member_avatar.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_24dp))

        tv_already_account.visibility = View.VISIBLE
        bt_go_sign_in.text = resources.getString(R.string.bt_sign_in)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                tv_member_id.text = data?.getStringExtra(getString(R.string.member_id_key))
                tv_member_name.text = data?.getStringExtra(getString(R.string.member_name_key))
                Glide.with(this)
                        .load(data?.getStringExtra(getString(R.string.member_avatar_key)))
                        .into(iv_member_avatar)
                tv_already_account.visibility = View.INVISIBLE
                initButtonSignSignOut()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
