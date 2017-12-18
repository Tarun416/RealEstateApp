package com.talisman.app.ui.tickets

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.*
import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.tickets.model.Entry
import com.talisman.app.ui.tickets.model.TicketResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Tarun on 11/24/17.
 */
class TicketPresenter
@Inject
constructor(val view: TicketContract.View, val database : AppDataBase) : TicketContract.Presenter {

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
            jsonObject.put("user_name", preferences.userName)
            jsonObject.put("password",preferences.passwordInMd5)
            parent.put("user_auth", jsonObject)
            Timber.d("output", parent.toString())

            hitLoginApiForParticularCustomer()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun hitLoginApiForParticularCustomer() {
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


    override fun crmLogin() {

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
                        getTickets(t!!.id)
                        //view.hideProgress()
                        //view.showRecentCalls(t!!.CDRJSON)
                    }

                })


        compositeDisposable.add(disposable)
    }

    override fun getTickets(id: String) {

        try {
            ticketJsonObject.put("session", id)
            ticketJsonObject.put("module_name", "Cases")
            ticketJsonObject.put("query", "cases.account_id" + " = '" + preferences.crmbusinessid + "'")
            ticketJsonObject.put("order_by", "cases.date_entered")
            ticketJsonObject.put("offset", 0)
            ticketJsonObject.put("select_fields","")
            ticketJsonObject.put("max_results", 1000)
            ticketJsonObject.put("deleted", "false")

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
                .map { ticketResponse ->
                    database.tickets().deleteTickets()
                    database.tickets().insertTickets(ticketResponse)
                    ticketResponse
                }
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


    override fun getTicketsFromDb()
    {
        val results = ArrayList<Entry>()

        compositeDisposable.add(database.tickets().getTickets()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())

                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({ ticketResponse ->

                    results.clear()
                    results.addAll(ticketResponse.entry_list)
                    computeTicketData(results)

                }, { throwable -> Log.e(TicketFragment.TAG, "Unable to fetch customer response", throwable) }))
    }

    private fun computeTicketData(results: ArrayList<Entry>) {
        val computeDisposable = Flowable.just<List<Entry>>(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Entry>>() {
                    override fun onNext(t: List<Entry>?) {
                        if(t!!.isNotEmpty())
                            view.showTickets(t!!)
                        else
                            view.showEmptyView()
                    }

                    override fun onError(t: Throwable?) {

                    }

                    override fun onComplete() {

                    }

                })

        compositeDisposable.add(computeDisposable)
    }

    fun onStop() {
        compositeDisposable.clear()
    }


}
