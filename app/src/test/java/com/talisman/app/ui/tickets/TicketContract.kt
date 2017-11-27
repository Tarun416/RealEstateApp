package com.talisman.app.ui.tickets

import com.talisman.app.BaseView
import com.talisman.app.ui.tickets.model.Entry

/**
 * Created by Tarun on 11/24/17.
 */
interface TicketContract
{
    interface View : BaseView
    {
        fun crmLoginDone()
       fun showTickets(entry_list: List<Entry>)
        fun onError(message: String?)
        fun showEmptyView()
    }

    interface Presenter
    {
        fun crmLogin()
        fun getTickets(id: String)
    }
}