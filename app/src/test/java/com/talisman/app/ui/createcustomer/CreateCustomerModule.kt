package com.talisman.app.ui.createcustomer

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 12/5/17.
 */
@Module
class CreateCustomerModule(private val mView: CreateCustomerContract.View) {

    @Provides
    @CustomScope
    internal fun providesCreateCustomerContractView(): CreateCustomerContract.View {
        return mView
    }
}