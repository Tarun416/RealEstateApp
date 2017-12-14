package com.talisman.app.ui.settings.model

import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 12/14/17.
 */

data class ChangePasswordResponse(
		val error: String, //success
		val code: Int, //200
		val data: Data
) : BaseResponse()

data class Data(
		val msg: String //New Password is updated successfully
)