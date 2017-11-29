package com.talisman.app

import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.customers.model.CustomerResponse
import com.talisman.app.ui.forgotpassword.model.ForgotPasswordResponse
import com.talisman.app.ui.login.model.LogInResponse
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import com.talisman.app.ui.settings.model.SettingsResponse
import com.talisman.app.ui.ticketdetails.TicketUpdateResponse
import com.talisman.app.ui.tickets.model.TicketResponse
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by Tarun on 11/17/17.
 */
interface ApiInterface {

    @POST("login")
    fun login(@Body loginHashMap: HashMap<String, String>): Flowable<LogInResponse>

    @POST("forgot_password")
    fun forgotPassword(@Body forgotPwdHashMap: HashMap<String, String>): Flowable<ForgotPasswordResponse>

    @POST("update_fcm_details")
    fun updateFCMDetails(@Body fcmDetailsHashMap: HashMap<String, String>): Flowable<ForgotPasswordResponse>

    @POST("update_login_status")
    fun updateLoginStatus(@Body hashMap: HashMap<String, String>): Flowable<SettingsResponse>

    @POST("getcrmlead_details")
    fun getCustomerDetails(@Body hashMap: HashMap<String, String>): Flowable<CustomerDetailsResponse>

    @GET("report/cdr")
    fun getRecentCalls(@Header("Content-Type") contentType: String,
                       @Header("developer_id") developerId: String,
                       @Header("x-fromDateTime") fromDateTime : String,
                       @Header("x-toDateTime") toDateTime : String,
                     //  @Header("x-business-id") referenceNo : String,
                       @Header("x-actual-number") actualNumber: String,
                       @Header("x-appType") appType : String): Flowable<RecentCallResponse>


    @POST("rest.php")
    fun crmLogin(@Query("method") method : String,
                           @Query("input_type") inputType : String,
                           @Query("response_type") responseType : String,
                           @Query("rest_data") restData: String) : Flowable<CRMLoginResponse>

    @POST("rest.php")
    fun getCustomers(@Query("method") method : String,
                 @Query("input_type") inputType : String,
                 @Query("response_type") responseType : String,
                 @Query("rest_data") restData: String) : Flowable<CustomerResponse>



    @POST("rest.php")
    fun getTickets(@Query("method") method : String,
                     @Query("input_type") inputType : String,
                     @Query("response_type") responseType : String,
                     @Query("rest_data") restData: String) : Flowable<TicketResponse>

    @POST("rest.php")
    fun updateTicket(@Query("method") method : String,
                   @Query("input_type") inputType : String,
                   @Query("response_type") responseType : String,
                   @Query("rest_data") restData: String) : Flowable<TicketUpdateResponse>





}