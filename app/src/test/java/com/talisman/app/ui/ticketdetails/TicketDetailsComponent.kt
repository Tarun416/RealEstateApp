package com.talisman.app.ui.ticketdetails

import com.talisman.app.NetComponent
import com.talisman.app.ui.recentcalldetails.ticketdetails.TicketCreateActivity
import com.talisman.app.utils.CustomScope
import dagger.Component
import dagger.Module

/**
 * Created by Tarun on 11/29/17.
 */
@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(TicketDetailsModule::class))
interface TicketDetailsComponent {
    fun inject(activity: TicketDetailsActivity)
    fun inject(activity: TicketCreateActivity)


}