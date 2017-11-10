package com.talisman.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.talisman.app.ui.login.model.LoginResponse

/**
 * Created by varun on 11/8/17.
 */
@Database(
        version = 1,
        entities = arrayOf(LoginResponse::class)
)
abstract  class AppDataBase : RoomDatabase()
{

}
