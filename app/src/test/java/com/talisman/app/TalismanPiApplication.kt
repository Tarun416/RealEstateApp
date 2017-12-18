package com.talisman.app

import android.app.Application
import android.arch.persistence.room.Room
import com.example.tarun.kotlin.Preferences
import com.squareup.leakcanary.LeakCanary

/**
 * Created by varun on 11/8/17.
 */
class TalismanPiApplication : Application() {
    companion object {
        lateinit var mNetComponent: NetComponent
        lateinit var appDatabase: AppDataBase
        private const val DB_NAME = "talisman-pi.db"
        lateinit var instance: TalismanPiApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appDatabase = Room.databaseBuilder(this, AppDataBase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
        Preferences.init(applicationContext)
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this@TalismanPiApplication)
        mNetComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this@TalismanPiApplication))
                .netModule(NetModule(com.example.tarun.talismanpi.BuildConfig.SERVER_URL, this@TalismanPiApplication))
                .build()

        TalismanPiApplication.mNetComponent.inject(this@TalismanPiApplication)

    }


    fun getDatabase(): AppDataBase {
        return appDatabase
    }


}