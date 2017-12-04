package com.talisman.app.ui.recentcalldetails.ticketdetails

import com.talisman.app.NetComponent
import com.talisman.app.utils.CustomScope
import dagger.Component

/**
 * Created by Tarun on 12/4/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(TicketModule::class))
interface TicketRDetailsComponent {
    fun inject(fragment: TicketFragment)

}