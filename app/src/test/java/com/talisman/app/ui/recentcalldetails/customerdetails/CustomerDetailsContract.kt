package com.talisman.app.ui.recentcalldetails.customerdetails

import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/23/17.
 */
interface CustomerDetailsContract
{
    interface View : BaseView
    {

    }

    interface presenter
    {
        fun crmLogin()
    }
}