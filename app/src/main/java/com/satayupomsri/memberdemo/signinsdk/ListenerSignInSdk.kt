package com.satayupomsri.memberdemo.signinsdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import com.satayupomsri.memberdemo.R
import com.satayupomsri.memberdemo.SignInActivity
import com.satayupomsri.memberdemo.signinsdk.utils.Prefs

/**
 * Created by satayupomsri on 15/11/2018 AD.
 */
class ListenerSignInSdk(context: Context) {

    private val activity: Activity = (context as Activity)
    private val intent: Intent
    private val resources: Resources
    private val prefs: Prefs

    init {
        intent = activity.intent
        resources = activity.resources
        prefs = Prefs(context)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == resources.getString(com.satayupomsri.membersdkdemo.R.string.type_text)) {
                    handleSignInSdk(intent)
                }
            }
        }
    }

    fun provideData(consumerPackage: String, id: String, name: String, avatar: String) {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key), activity.packageName)
                putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_id_key), id)
                putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_name_key), name)
                putExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.member_avatar_key), avatar)
                putExtra(
                        resources.getString(com.satayupomsri.membersdkdemo.R.string.member_status_sign_in_key),
                        resources.getString(com.satayupomsri.membersdkdemo.R.string.member_status_sign_in_success))
                type = resources.getString(com.satayupomsri.membersdkdemo.R.string.type_text)
            }

            sendIntent.`package` = consumerPackage
            activity.startActivity(sendIntent)
           activity.finish()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, resources.getString(R.string.alert_no_activity_handle_intent))
        }
    }

    private fun handleSignInSdk(intent: Intent) {
        intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key))?.let {
            intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))?.let {
                if(intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.provider_package_key)) == activity.packageName) {
                    val packageApp = intent.getStringExtra(resources.getString(com.satayupomsri.membersdkdemo.R.string.consumer_package_key))
                    if(prefs.memberData.isSignIn()) {
                        val (id, name, avatar) = prefs.memberData.getMemberData()
                        //already sign in
                        provideData(packageApp, id, name, avatar)
                    } else {
                        //no member sign in
                        val sendIntent: Intent = Intent(activity, SignInActivity::class.java).apply {
                            action = Intent.ACTION_SEND
                            putExtra(resources.getString(R.string.provider_package_key), resources.getString(R.string.provider_package_name))
                            putExtra(resources.getString(R.string.consumer_package_key), packageApp)
                        }
                        activity.startActivity(sendIntent)
                        activity.finish()
                    }
                }
            }
        }
    }
}