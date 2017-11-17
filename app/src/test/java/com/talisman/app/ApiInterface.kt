package com.talisman.app

import com.talisman.app.ui.login.model.LogInResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Tarun on 11/17/17.
 */
interface ApiInterface {

    @POST("login")
    fun login(@Body loginHashMap : HashMap<String,String> ) : Flowable<LogInResponse>
}