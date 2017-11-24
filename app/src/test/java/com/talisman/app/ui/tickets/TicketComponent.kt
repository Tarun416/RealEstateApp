package com.talisman.app.ui.tickets

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 11/24/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(TicketModule::class))
interface TicketComponent {
    fun inject(fragment: TicketFragment)

}