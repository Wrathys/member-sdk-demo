package com.satayupomsri.memberdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.satayupomsri.memberdemo.signinsdk.ListenerSignInSdk
import com.satayupomsri.memberdemo.signinsdk.MemberData
import com.satayupomsri.memberdemo.signinsdk.utils.Prefs
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private var consumerPackageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        if(intent?.action == Intent.ACTION_SEND) {
            intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key))?.let {
                intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))?.let {
                    if (intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key)) == this.packageName) {
                        consumerPackageName = intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))
                    }
                }
            }
        }

        bt_sign_in.setOnClickListener {
            val e = et_sign_in_email.text
            val p = et_sign_in_password.text
            if((e.toString().trim() == "user" || e.toString().trim() == "0987654321") && p.toString().trim() == "pass") {
                val pref = Prefs(this)
                pref.memberData = MemberData("m00001", "User General", true)

                val (id, name) = pref.memberData.getMemberData()

                if(!consumerPackageName.isNullOrEmpty()){
                    ListenerSignInSdk(this).provideData(consumerPackageName!!, id, name)
                } else {
                    Toast.makeText(applicationContext, resources.getString(R.string.welcome_sign_in, name), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            } else {
                Toast.makeText(applicationContext, resources.getString(R.string.warning_invalid_sign_in), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
