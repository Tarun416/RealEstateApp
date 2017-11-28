package com.talisman.app.ui.login

import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/16/17.
 */
interface LoginContract
{
    interface View : BaseView
    {
        fun login()
        fun invalidCredentialError(message : String)
        fun onDeviceRegistered()
    }

    interface Presenter
    {
        fun login(email : String, password : String)
        fun sendRegistrationToken()
    }
}