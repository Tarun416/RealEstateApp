package com.talisman.app.ui.settings

import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/27/17.
 */
interface SettingsContract
{
    interface View : BaseView
    {
        fun showSuccessMessage()
        fun failed()
        fun showSuccessMessage(message : String)

    }

    interface Presenter
    {
       fun setStatus ( status : String)
        fun changePassword( currentPassword : String, newPassword : String)
    }
}