package com.talisman.app.ui.ticketdetails

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/29/17.
 */
@Module
class TicketDetailsModule(private val mView: TicketDetailsContract.View) {

    @Provides
    @CustomScope
    internal fun providesTicketDetailsContractView(): TicketDetailsContract.View {
        return mView
    }
}