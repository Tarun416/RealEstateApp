package com.talisman.app.ui.customers

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/23/17.
 */
@Module
class CustomerModule(private val mView: CustomerContract.View) {

    @Provides
    @CustomScope
    internal fun providesCustomerDetailsContractView(): CustomerContract.View {
        return mView
    }
}