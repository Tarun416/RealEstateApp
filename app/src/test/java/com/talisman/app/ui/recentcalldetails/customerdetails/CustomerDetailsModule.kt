package com.talisman.app.ui.recentcalldetails.customerdetails

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/23/17.
 */
@Module
class CustomerDetailsModule(private val mView: CustomerDetailsContract.View) {

    @Provides
    @CustomScope
    internal fun providesCustomerDetailsContractView(): CustomerDetailsContract.View {
        return mView
    }
}