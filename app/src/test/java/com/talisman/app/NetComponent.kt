package com.talisman.app

import dagger.Component
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by varun on 11/8/17.
 */
@Singleton
@Component(modules = arrayOf( AppModule::class,NetModule::class))
interface NetComponent {
    fun inject(app: TalismanPiApplication)
    fun cache() : Cache
    fun okHttp() : OkHttpClient
    fun retrofit() : Retrofit
    fun preferenceManager() :TalismanPiPreferences
    fun database() : AppDataBase
}
