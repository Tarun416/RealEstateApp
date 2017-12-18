package com.talisman.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.talisman.app.ui.login.model.LogInResponse
import com.talisman.app.ui.login.model.SignInResponse
import com.talisman.app.ui.recentcalls.RecentCallDao
import com.talisman.app.ui.recentcalls.model.RecentCallResponse

/**
 * Created by varun on 11/8/17.
 */
@Database(
        version = 1,
        entities = arrayOf(RecentCallResponse::class)
)
@TypeConverters(Converters::class)
abstract  class AppDataBase : RoomDatabase()
{
   abstract fun getRecentCalls() : RecentCallDao
}
