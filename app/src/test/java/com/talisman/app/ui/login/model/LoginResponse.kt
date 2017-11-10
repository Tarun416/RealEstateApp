package com.talisman.app.ui.login.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by varun on 11/8/17.
 */
@Entity()
class LoginResponse()
{
    @PrimaryKey(autoGenerate = true)
    var uid: Long?=0
}
