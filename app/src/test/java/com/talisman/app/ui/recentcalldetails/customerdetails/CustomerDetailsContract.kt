package com.talisman.app.ui.recentcalldetails.customerdetails

import com.talisman.app.BaseView
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse

/**
 * Created by Tarun on 11/23/17.
 */
interface CustomerDetailsContract
{
    interface View : BaseView
    {
        fun passCustomerDetails(t: CustomerDetailsResponse)
        fun showErrorMesssage(message: String)
    }

    interface Presenter
    {

        fun getCustomerDetails(phone : String)
    }
}