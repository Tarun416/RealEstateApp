package com.talisman.app.ui.recentcalldetails.customerdetails

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/23/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(CustomerDetailsModule::class))
interface CustomerDetailsComponent {
    fun inject(fragment: CustomerDetailsFragment)

}