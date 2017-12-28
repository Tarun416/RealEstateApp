package com.talisman.app.ui.createcustomer

import com.talisman.app.BaseView

/**
 * Created by Tarun on 12/5/17.
 */
interface CreateCustomerContract
{
    interface View : BaseView
    {
        fun onSuccess()
    }

    interface Presenter
    {
        fun crmLogin(firstName :String , lastName : String , phone: String,
                     street : String, city : String, state: String,
                     country : String , pincode : String, id : String,description  : String , title : String)
    }
}