package com.talisman.app.ui.login

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/16/17.
 */
@Module
class LoginModule(private val mView: LoginContract.View) {

    @Provides
    @CustomScope
    internal fun providesLoginContractView(): LoginContract.View {
        return mView
    }
}