package com.talisman.app.ui.settings

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/27/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(SettingsModule::class))
interface SettingsComponent {
    fun inject(activity: SettingsActivity)

}