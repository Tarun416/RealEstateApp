package com.talisman.app.ui.login

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/16/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(LoginModule::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)

}