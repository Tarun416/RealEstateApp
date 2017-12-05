package com.talisman.app.ui.createcustomer

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 12/5/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(CreateCustomerModule::class))
interface CreateCustomerComponent {
    fun inject(activity: CreateCustomerActivity)
}