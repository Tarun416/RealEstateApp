package com.talisman.app.ui.recentcalls

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/21/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(RecentCallModule::class))
interface RecentCallComponent {
    fun inject(fragment: RecentCallFragment)

}