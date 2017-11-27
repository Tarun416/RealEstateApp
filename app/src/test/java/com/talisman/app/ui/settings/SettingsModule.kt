package com.talisman.app.ui.settings

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/27/17.
 */
@Module
class SettingsModule(private val mView: SettingsContract.View) {

    @Provides
    @CustomScope
    internal fun providesSettingsContractView(): SettingsContract.View {
        return mView
    }
}