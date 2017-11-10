package com.talisman.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by varun on 11/8/17.
 */
@Module
class AppModule(private val mApplication: TalismanPiApplication) {

    @Provides
    @Singleton
    fun providesApplication() = mApplication
}