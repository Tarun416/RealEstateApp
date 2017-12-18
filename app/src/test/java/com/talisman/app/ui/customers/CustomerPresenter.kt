package com.talisman.app.ui.customers

import android.util.Log
import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.*
import com.talisman.app.model.CRMLoginResponse
import com.talisman.app.ui.customers.model.CustomerResponse
import com.talisman.app.ui.customers.model.Entry
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject
import org.json.JSONException

import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber


/**
 * Created by Tarun on 11/23/17.
 */
class CustomerPresenter
@Inject
constructor(val retrofit : Retrofit, val view: CustomerContract.View, val database : AppDataBase) : CustomerContract.Presenter {

    private val preferences: TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface: ApiInterface
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val parent = JSONObject()
    private val jsonObject = JSONObject()
    private val customerJsonObject = JSONObject()

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
            customerJsonObject.put("query", "leads.account_id" + " = '" + preferences.crmbusinessid + "'")
            customerJsonObject.put("order_by", "leads.last_name")
            customerJsonObject.put("offset", 0)
            customerJsonObject.put("select_fields", "")
            customerJsonObject.put("max_results", 1000)
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
                .map { customerResponse ->
                    database.customers().deleteCustomers()
                    database.customers().insertCustomers(customerResponse)
                    customerResponse
                }
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
                        if(t!!.result_count>0)
                        view.showCustomers(t!!.entry_list)
                        else
                            view.showEmptyView()
                    }

                })


        compositeDisposable.add(disposable1)
    }


    override fun getCustomersFromDb()
    {
        val results = ArrayList<Entry>()

        compositeDisposable.add(database.customers().getCustomers()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())

                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({ customerResponse ->

                    results.clear()
                    results.addAll(customerResponse.entry_list)
                    computeCustomerData(results)

                }, { throwable -> Log.e(CustomerFragment.TAG, "Unable to fetch customer response", throwable) }))
    }


    private fun computeCustomerData(results: ArrayList<Entry>) {
        val computeDisposable = Flowable.just<List<Entry>>(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Entry>>() {
                    override fun onNext(t: List<Entry>?) {
                        if(t!!.isNotEmpty())
                            view.showCustomers(t!!)
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

    override fun getCustomerDetails(mobileNo: String) {
        view.showProgress()
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.put("prospectnumber", mobileNo)

        val disposable1 =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  retrofit.create(ApiInterface::class.java).getCustomerDetails(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<CustomerDetailsResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                        view.passCustomerDetails(null)
                    }

                    override fun onComplete() {

                    }

                   override fun onNext(t: CustomerDetailsResponse?) {
                        view.hideProgress()
                        view.passCustomerDetails(t!!)
                    }

                })


        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }


}
