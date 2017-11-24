package com.talisman.app.ui.recentcalls

import com.talisman.app.utils.CustomScope
import dagger.Module
import dagger.Provides

/**
 * Created by Tarun on 11/21/17.
 */
@Module
class RecentCallModule(private val mView: RecentCallContract.View) {

    @Provides
    @CustomScope
    internal fun providesRecentCallContractView(): RecentCallContract.View {
        return mView
    }
}