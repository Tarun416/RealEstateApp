package com.talisman.app.ui.forgotpassword

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/17/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(ForgotPasswordModule::class))

interface ForgotPasswordComponent {
    fun inject(activity: ForgotPasswordActivity)

}