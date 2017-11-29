package com.talisman.app.ui.ticketdetails

import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/29/17.
 */
interface TicketDetailsContract
{

    interface View : BaseView
    {
        fun updateSuccess()

    }

    interface Presenter
    {
        fun crmLogin(toString: String, toString1: String, toString2: String, toString3: String, toString4: String)
        fun updateTicket(id : String)
    }
}