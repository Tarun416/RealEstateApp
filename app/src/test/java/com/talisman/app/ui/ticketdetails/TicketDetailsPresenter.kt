package com.talisman.app.ui.ticketdetails

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.ApiInterface
import com.talisman.app.ApiUtils
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.model.CRMLoginResponse
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
 * Created by Tarun on 11/29/17.
 */
class TicketDetailsPresenter
@Inject
constructor(val retrofit: Retrofit, val view: TicketDetailsContract.View) : TicketDetailsContract.Presenter {

    override fun crmCreateLogin(toString: String, toString1: String, toString2: String, toString3: String, toString4: String, string5: String) {

    }

    private val preferences: TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface: ApiInterface
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val parent = JSONObject()
    private val jsonObject = JSONObject()
    private val ticketUpdateJsonObejct = JSONObject()
    private val nameValueListJsonArray = JSONArray()

    private val nameJsonObject = JSONObject()
    private val stateJsonObject = JSONObject()
    private val priorityJsonObject = JSONObject()
    private val resolutionJsonObject = JSONObject()
    private val descriptionJsonObject = JSONObject()
    private val accountIdJsonObject = JSONObject()
    private val idJsonObject = JSONObject()

    private lateinit var state: String
    private lateinit var priority : String
    private lateinit var description: String
    private lateinit var resolution : String
    private lateinit var ticketId : String

    override fun crmLogin(state: String, priority: String, description: String, resolution: String,ticketId: String) {

        this.state = state
        this.priority = priority
        this.description = description
        this.resolution = resolution
        this.ticketId = ticketId

        try {
            jsonObject.put("user_name", preferences.userName)
            jsonObject.put("password",preferences.passwordInMd5)
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
                        updateTicket(t!!.id)

                    }

                })


        compositeDisposable.add(disposable)
    }

    override fun updateTicket(id: String) {
        try {

            nameJsonObject.put("name","name")
            nameJsonObject.put("value","testing")

            stateJsonObject.put("name","state")
            stateJsonObject.put("value",state)

            priorityJsonObject.put("name","priority")
            priorityJsonObject.put("value",priority)

            resolutionJsonObject.put("name","resolution")
            resolutionJsonObject.put("value",resolution)

            descriptionJsonObject.put("name","description")
            descriptionJsonObject.put("value",description)

            accountIdJsonObject.put("name","account_id")
            accountIdJsonObject.put("value",preferences.crmbusinessid)

            idJsonObject.put("name","id")
            idJsonObject.put("value",ticketId)

            nameValueListJsonArray.put(nameJsonObject)
            nameValueListJsonArray.put(stateJsonObject)
            nameValueListJsonArray.put(priorityJsonObject)
            nameValueListJsonArray.put(resolutionJsonObject)
            nameValueListJsonArray.put(accountIdJsonObject)
            nameValueListJsonArray.put(idJsonObject)
            nameValueListJsonArray.put(descriptionJsonObject)


            ticketUpdateJsonObejct.put("session", id)
            ticketUpdateJsonObejct.put("module_name", "Cases")
            ticketUpdateJsonObejct.put("name_value_list", nameValueListJsonArray)
            Log.d("output", ticketUpdateJsonObejct.toString())
            hitTicketUpdateApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun hitTicketUpdateApi() {
        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.updateTicket("set_entry", "JSON", "JSON", ticketUpdateJsonObejct.toString())
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
                        view.updateSuccess()

                    }

                })


        compositeDisposable.add(disposable1)
    }


    fun onStop() {
        compositeDisposable.clear()
    }

}