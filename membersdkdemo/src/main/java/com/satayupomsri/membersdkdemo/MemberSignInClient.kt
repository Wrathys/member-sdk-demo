package com.satayupomsri.membersdkdemo

import android.content.Context
import android.view.View

/**
 * Created by satayupomsri on 3/12/2018 AD.
 */
class MemberSignInClient(private var context: Context) {

    private var memberSignInManager = MemberSignInManager(this.context)

    fun setCustomButton(view: View?) {
        view?.setOnClickListener { _ -> this.memberSignInManager.signInSignOut() }
    }

    fun setOnSignInListener(listener: com.satayupomsri.membersdkdemo.MemberSignInListener) {
        this.memberSignInManager.setOnSignInListener(listener)
    }
}