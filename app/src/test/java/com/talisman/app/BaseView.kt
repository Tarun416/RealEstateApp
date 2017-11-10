package com.talisman.app

/**
 * Created by varun on 11/8/17.
 */
interface BaseView {

    val isNetworkConnected: Boolean

    fun showProgress(message: String)

    fun showProgress()

    fun hideProgress()

    fun onUnknownError(message: String, error: String)

    fun onTimeout()

    fun onNetworkError()

    fun onFailure(errorMsg: String)

    fun onConnectionError()
}