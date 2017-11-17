package com.talisman.app.ui.login.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Tarun on 11/17/17.
 */
@Entity()
class SignInResponse()
{
    @PrimaryKey(autoGenerate = true)
    var uid: Long?=0
}