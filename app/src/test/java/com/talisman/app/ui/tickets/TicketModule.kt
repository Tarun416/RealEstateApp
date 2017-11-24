package com.talisman.app.ui.tickets

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/24/17.
 */
@Module
class TicketModule(private val mView: TicketContract.View) {

    @Provides
    @CustomScope
    internal fun providesTicketContractView(): TicketContract.View {
        return mView
    }
}