package com.talisman.app.ui.customers

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.ApiInterface
import com.talisman.app.ApiUtils
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.customers.model.CustomerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject
import org.json.JSONException

import org.json.JSONObject
import timber.log.Timber


/**
 * Created by Tarun on 11/23/17.
 */
class CustomerPresenter
@Inject
constructor(val view: CustomerContract.View) : CustomerContract.Presenter {

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
                        getCustomers(t!!.id)
                        //view.hideProgress()
                        //view.showRecentCalls(t!!.CDRJSON)
                    }

                })


        compositeDisposable.add(disposable)
    }

    override fun getCustomers(id: String) {

        try {
            customerJsonObject.put("session", id)
            customerJsonObject.put("module_name", "Leads")
            customerJsonObject.put("query", "leads.account_id" + " = '" + preferences.businessid + "'")
            customerJsonObject.put("order_by", "leads.last_name")
            customerJsonObject.put("offset", 0)
            customerJsonObject.put("select_fields", "")
            customerJsonObject.put("max_results", 50)
            customerJsonObject.put("deleted", "false")
            //customerJsonObject.put("link_name_to_fields_array","")

            Log.d("output", customerJsonObject.toString())

            hitCustomerApi()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun hitCustomerApi() {

        apiInterface = ApiUtils.getApiService(BuildConfig.CRM_SERVER_URL, TalismanPiApplication.instance)
        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.getCustomers("get_entry_list", "JSON", "JSON", customerJsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<CustomerResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                        view.onError(t!!.message)
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: CustomerResponse?) {
                        view.hideProgress()
                        view.showCustomers(t!!.entry_list)
                    }

                })


        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }


}
