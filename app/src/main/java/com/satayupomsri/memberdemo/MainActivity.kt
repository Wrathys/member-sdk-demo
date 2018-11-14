package com.satayupomsri.memberdemo

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
            override fun onSignInFail(status: Int) {
                Toast.makeText(applicationContext, "Fail status: $status", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
