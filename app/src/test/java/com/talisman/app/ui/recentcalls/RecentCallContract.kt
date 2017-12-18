package com.talisman.app.ui.recentcalls

import com.talisman.app.BaseView
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.ui.recentcalls.model.CDRJSON

/**
 * Created by Tarun on 11/21/17.
 */
interface RecentCallContract
{

    interface View : BaseView
    {
       fun showRecentCalls(list : ArrayList<CDRJSON>)
        fun resultError()
        fun passCustomerDetails(customerDetailsResponse: CustomerDetailsResponse?)
    }

    interface Presenter
    {
        fun getRecentCalls(xli : String)
        fun getCustomerDetails(mobileNo : String)
        fun getRecentCallsFromDb()
    }
}