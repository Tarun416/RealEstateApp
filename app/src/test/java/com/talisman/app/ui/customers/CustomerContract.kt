package com.talisman.app.ui.customers

import com.talisman.app.BaseView
import com.talisman.app.ui.customers.model.Entry
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse

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
        fun passCustomerDetails(customerDetailsResponse: CustomerDetailsResponse?)
        fun showEmptyView()
    }

    interface Presenter
    {
        fun crmLogin()
        fun getCustomers(id: String)
        fun getCustomerDetails(mobileNo : String)
    }
}