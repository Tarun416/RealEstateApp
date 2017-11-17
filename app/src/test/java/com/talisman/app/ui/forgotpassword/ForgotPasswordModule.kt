package com.talisman.app.ui.forgotpassword

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/17/17.
 */
@Module
class ForgotPasswordModule(private val mView: ForgotPasswordContract.View) {

    @Provides
    @CustomScope
    internal fun providesForgotPasswordContractView(): ForgotPasswordContract.View {
        return mView
    }
}