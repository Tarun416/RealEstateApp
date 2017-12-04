package com.talisman.app.ui.recentcalldetails.ticketdetails

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 12/4/17.
 */
@Module
class TicketModule(private val mView: TicketContract.View) {

    @Provides
    @CustomScope
    internal fun providesticketContractView(): TicketContract.View {
        return mView
    }
}