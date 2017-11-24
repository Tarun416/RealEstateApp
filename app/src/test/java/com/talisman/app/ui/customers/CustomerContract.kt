package com.talisman.app.ui.customers

import com.talisman.app.BaseView
import com.talisman.app.ui.customers.model.Entry

/**
 * Created by Tarun on 11/23/17.
 */
interface CustomerContract
{
    interface View : BaseView
    {
       fun crmLoginDone()
        fun showCustomers(entry_list: List<Entry>)
        fun onError(message: String?)
    }

    interface Presenter
    {
        fun crmLogin()
        fun getCustomers(id: String)
    }
}