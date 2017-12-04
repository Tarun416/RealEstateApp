package com.talisman.app.ui.recentcalldetails.ticketdetails

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.ApiInterface
import com.talisman.app.ApiUtils
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.tickets.model.TicketResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Tarun on 12/4/17.
 */
class TicketPresenter
@Inject
constructor(val view: TicketContract.View) : TicketContract.Presenter {

    private val preferences: TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface: ApiInterface
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val parent = JSONObject()
    private val jsonObject = JSONObject()
    private val ticketJsonObject = JSONObject()
    private lateinit var phone : String

    override fun crmLogin(phone : String) {
        this.phone = phone

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
                        getTicketsForParticularCustomer(t!!.id)
                        //view.hideProgress()
                        //view.showRecentCalls(t!!.CDRJSON)
                    }

                })


        compositeDisposable.add(disposable)
    }


    /*"{"session":"hogvtpljnotu22o7rjonhib3n3","module_name":"Cases","query":"cases.account_id = '71f5f2e7-a526-7f4b-a1be-5a0be099ae9f'
    AND cases.work_log = 8951577970","order_by":"cases.date_entered","offset":"",
    "select_fields":["name","case_number","state","priority","assigned_user_id"],
    "link_name_to_fields_array":[[]],"max_results":1000,"deleted":0}"*/


    private fun getTicketsForParticularCustomer(id: String) {
        try {
            ticketJsonObject.put("session", id)
            ticketJsonObject.put("module_name", "Cases")
            ticketJsonObject.put("query", "cases.account_id" + " = '" + preferences.crmbusinessid + "' AND "+"cases.work_log" + " = " + phone.substring(1,phone.length))
           // ticketJsonObject.put("case.work_log",phone.substring(1,phone.length))
            ticketJsonObject.put("order_by", "cases.date_entered")
            ticketJsonObject.put("offset", "")
            ticketJsonObject.put("select_fields","")
            ticketJsonObject.put("max_results", 1000)
            ticketJsonObject.put("deleted", 0)
            Log.d("output", ticketJsonObject.toString())
            hitTicketsApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun hitTicketsApi() {

        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.getTickets("get_entry_list", "JSON", "JSON", ticketJsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<TicketResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                        view.onError(t!!.message)
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: TicketResponse?) {
                        view.hideProgress()
                        if(t!!.result_count>0)
                            view.showTickets(t!!.entry_list)
                        else
                            view.showEmptyView()
                    }

                })


        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }
}




