package com.satayupomsri.memberdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.satayupomsri.membersdkdemo.SignOnButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_sign_on.setOnSigninListener(object : SignOnButton.OnSignInListener {
            override fun onSignInSuccess(id: String, name: String) {
                Toast.makeText(applicationContext, "Success id: $id name: $name", Toast.LENGTH_SHORT).show()
            }
            override fun onSignInFail(status: String) {
                Toast.makeText(applicationContext, "Fail status: $status", Toast.LENGTH_SHORT).show()
            }
        })

        // Listen to provide the data that request by sdk sign in
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (resources.getString(com.satayupomsri.membersdkdemo.R.string.type_text) == intent.type) {
                    handleSendText(intent)
                }
            }
        }
    }

    private fun provideData(consumerPackage: String, id: String, name: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key), applicationContext.packageName)
            putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_id_key), id)
            putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_name_key), name)

            val status = if (Math.random() < 0.5)
                resources.getString(com.satayupomsri.membersdkdemo.R.string.member_status_sign_in_success)
            else
                resources.getString(com.satayupomsri.membersdkdemo.R.string.member_status_sign_in_fail)
            putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_status_sign_in_key), status)

            type = resources.getString(com.satayupomsri.membersdkdemo.R.string.type_text)
        }

        sendIntent.`package` = consumerPackage
        startActivity(sendIntent)
        finish()
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key))?.let {
            intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))?.let {
                if(intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key)) == applicationContext.packageName) {
                    val packageApp = intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))
                    provideData(packageApp, "memberdemo", "fname lname")
                }
            }
        }
    }
}
