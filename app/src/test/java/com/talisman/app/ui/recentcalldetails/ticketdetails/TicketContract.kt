package com.talisman.app.ui.recentcalldetails.ticketdetails

import com.talisman.app.BaseView
import com.talisman.app.ui.tickets.model.Entry

/**
 * Created by Tarun on 12/4/17.
 */
interface TicketContract
{
    interface View : BaseView
    {
        fun showTickets(entry_list: List<Entry>)
        fun onError(message: String?)
        fun showEmptyView()
    }

    interface Presenter
    {
       fun crmLogin(phone : String)
    }
}