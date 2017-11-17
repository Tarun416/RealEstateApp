package com.talisman.app.ui.forgotpassword

import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/17/17.
 */
interface ForgotPasswordContract {

    interface View : BaseView {
       fun successResponse(message : String)
    }

    interface Presenter {
        fun forgotPassword(email: String)
    }
}