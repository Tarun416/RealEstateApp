package com.talisman.app.ui.recentcalls

import com.talisman.app.BaseResponse
import com.talisman.app.BaseView
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
    }

    interface Presenter
    {
        fun getRecentCalls()
    }
}