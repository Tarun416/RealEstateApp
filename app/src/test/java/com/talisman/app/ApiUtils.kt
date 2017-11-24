package com.talisman.app

/**
 * Created by Tarun on 11/21/17.
 */
class ApiUtils
{
    companion object {
        fun getApiService(baseUrl : String, application: TalismanPiApplication) : ApiInterface
        {
            return RetrofitClient.getClient(baseUrl,application).create(ApiInterface::class.java)
        }
    }


}