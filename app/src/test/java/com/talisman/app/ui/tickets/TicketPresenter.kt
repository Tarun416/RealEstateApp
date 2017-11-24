package com.talisman.app.ui.tickets

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
 * Created by Tarun on 11/24/17.
 */
class TicketPresenter
@Inject
constructor(val view: TicketContract.View) : TicketContract.Presenter {

    private val preferences: TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface: ApiInterface
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val parent = JSONObject()
    private val jsonObject = JSONObject()
    private val customerJsonObject = JSONObject()

    override fun crmLogin() {

        try {
            jsonObject.put("user_name", BuildConfig.USERNAME)
            jsonObject.put("password", BuildConfig.PASSWORD)
            parent.put("user_auth", jsonObject)
            Timber.d("output", parent.toString())

            hitApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun hitApi() {
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
            customerJsonObject.put("session", id)
            customerJsonObject.put("module_name", "Cases")
            customerJsonObject.put("query", "cases.account_id" + " = '" + preferences.businessid + "'")
            customerJsonObject.put("order_by", "cases.date_entered")
            customerJsonObject.put("offset", 0)
            customerJsonObject.put("select_fields","")
            customerJsonObject.put("max_results", 50)
            customerJsonObject.put("deleted", "false")

            Log.d("output",customerJsonObject.toString())

            hitTicketsApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun hitTicketsApi() {

        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.getTickets("get_entry_list", "JSON", "JSON", customerJsonObject.toString())
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
                        view.showTickets(t!!.entry_list)
                    }

                })


        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }


}
