package com.talisman.app.ui.forgotpassword.model

import com.talisman.app.BaseResponse
import com.talisman.app.BaseView

/**
 * Created by Tarun on 11/17/17.
 */

data class ForgotPasswordResponse(
		val error: String, //success
		val data: Data
) : BaseResponse()

data class Data(
		val msg: String //Reset Password link has been sent to your registered mail id
)