package com.talisman.app.ui.createcustomer

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.ApiInterface
import com.talisman.app.ApiUtils
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.ticketdetails.TicketUpdateResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Tarun on 12/5/17.
 */
class CreateCustomerPresenter
@Inject
constructor(val retrofit: Retrofit, val view: CreateCustomerContract.View) : CreateCustomerContract.Presenter {

    private var customerId: String = ""
    private var description: String = ""
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phone: String
    private lateinit var street: String
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var country: String
    private lateinit var pincode: String
    private lateinit var apiInterface: ApiInterface

    private val createJsonObject1 = JSONObject()
    private val createJsonObject2 = JSONObject()
    private val createJsonObject3 = JSONObject()
    private val createJsonObject4 = JSONObject()
    private val createJsonObject5 = JSONObject()
    private val createJsonObject6 = JSONObject()
    private val createJsonObject7 = JSONObject()
    private val createJsonObject8 = JSONObject()
    private val createJsonObject9 = JSONObject()
    private val createJsonObject10 = JSONObject()
    private val createJsonObject11 = JSONObject()
    private val createJsonObject12 = JSONObject()
    private val createJsonObject13 = JSONObject()
    private val idJsonObjectForUpdate = JSONObject()


    private val parent = JSONObject()
    private val jsonObject = JSONObject()
    private val customerCreateJsonObject = JSONObject()
    private val nameValueListJsonArray = JSONArray()

    private val compositeDisposable = CompositeDisposable()
    private val preferences: TalismanPiPreferences = TalismanPiPreferences()

    override fun crmLogin(firstName: String, lastName: String, phone: String,
                          street: String, city: String, state: String,
                          country: String, pincode: String, id : String,description : String) {

        this.firstName = firstName
        this.lastName = lastName
        this.phone = phone
        this.street = street
        this.city = city
        this.state = state
        this.country = country
        this.pincode = pincode
        this.customerId=id
        this.description=description

        try {
            jsonObject.put("user_name", BuildConfig.USERNAME)
            jsonObject.put("password", BuildConfig.PASSWORD)
            parent.put("user_auth", jsonObject)
            Timber.d("output", parent.toString())
            hitLoginApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }


    private fun hitLoginApi() {
        view.showProgress()
        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.crmLogin("login", "JSON", "JSON", parent.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<CRMLoginResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                        // view.resultError()
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: CRMLoginResponse?) {
                        createCustomer(t!!.id)

                    }

                })


        compositeDisposable.add(disposable)
    }

    private fun createCustomer(id: String) {
        try {

           if(description.isEmpty()) {

               createJsonObject1.put("name", "first_name")
               createJsonObject1.put("value", firstName)

               createJsonObject2.put("name", "title")
               createJsonObject2.put("value", "")

               createJsonObject3.put("name", "phone_mobile")
               createJsonObject3.put("value", "91" + phone)

               createJsonObject4.put("name", "phone_work")
               createJsonObject4.put("value", "91" + phone)

               createJsonObject5.put("name", "last_name")
               createJsonObject5.put("value", lastName)

               createJsonObject6.put("name", "status")
               createJsonObject6.put("value", "New")

//            createJsonObject7.put("name", "description")
               //          createJsonObject7.put("value", "")

               createJsonObject8.put("name", "primary_address_street")
               createJsonObject8.put("value", street)

               createJsonObject9.put("name", "primary_address_city")
               createJsonObject9.put("value", city)

               createJsonObject10.put("name", "primary_address_state")
               createJsonObject10.put("value", state)

               createJsonObject11.put("name", "primary_address_postalcode")
               createJsonObject11.put("value", pincode)

               createJsonObject12.put("name", "primary_address_country")
               createJsonObject12.put("value", country)

               createJsonObject13.put("name", "account_id")
               createJsonObject13.put("value", preferences.crmbusinessid)

               nameValueListJsonArray.put(createJsonObject1)

               if (!customerId.isEmpty()) {
                   idJsonObjectForUpdate.put("name", "id")
                   idJsonObjectForUpdate.put("value", customerId)
                   nameValueListJsonArray.put(idJsonObjectForUpdate)
               }

               nameValueListJsonArray.put(createJsonObject2)
               nameValueListJsonArray.put(createJsonObject3)
               nameValueListJsonArray.put(createJsonObject4)
               nameValueListJsonArray.put(createJsonObject5)
               nameValueListJsonArray.put(createJsonObject6)

               if (customerId.isEmpty()) {
                   createJsonObject7.put("name", "description")
                   createJsonObject7.put("value", "")
                   nameValueListJsonArray.put(createJsonObject7)
               }

               nameValueListJsonArray.put(createJsonObject8)
               nameValueListJsonArray.put(createJsonObject9)
               nameValueListJsonArray.put(createJsonObject10)
               nameValueListJsonArray.put(createJsonObject11)
               nameValueListJsonArray.put(createJsonObject12)
               nameValueListJsonArray.put(createJsonObject13)
           }
            else
           {
               createJsonObject1.put("name","description")
               createJsonObject1.put("value",description)

               idJsonObjectForUpdate.put("name", "id")
               idJsonObjectForUpdate.put("value", customerId)

               createJsonObject13.put("name", "account_id")
               createJsonObject13.put("value", preferences.crmbusinessid)

               nameValueListJsonArray.put(createJsonObject1)
               nameValueListJsonArray.put(idJsonObjectForUpdate)
               nameValueListJsonArray.put(createJsonObject13)
           }

            customerCreateJsonObject.put("session", id)
            customerCreateJsonObject.put("module_name", "Leads")
            customerCreateJsonObject.put("name_value_list", nameValueListJsonArray)

            Log.d("output", customerCreateJsonObject.toString())
            hitCustomerCreateApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun hitCustomerCreateApi() {
        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.updateTicket("set_entry", "JSON", "JSON", customerCreateJsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<TicketUpdateResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: TicketUpdateResponse?) {
                        view.hideProgress()
                        view.onSuccess()

                    }

                })

        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }

}