package com.talisman.app.ui.recentcalls

import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.*
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Tarun on 11/21/17.
 */
class RecentCallPresenter
@Inject constructor(var retrofit: Retrofit, var view: RecentCallContract.View) : RecentCallContract.Presenter {

    private val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
    private val preferences: TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface: ApiInterface
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun getRecentCalls() {
        view.showProgress()
        apiInterface = ApiUtils.getApiService(BuildConfig.CDR_SERVER_URL, TalismanPiApplication.instance)
        val disposable =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.getRecentCalls(Constants.CONTENT_TYPE, Constants.DEVELOPER_ID, "20170806110000",
                simpleDateFormat.format(Date()), preferences.agentNo!!, Constants.APP_TYPE , preferences.virtualNo!!.substring(1, preferences.virtualNo!!.length)!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<RecentCallResponse>() {

                    override fun onError(t: Throwable?) {
                        view.hideProgress()
                        view.resultError()
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: RecentCallResponse?) {
                        view.hideProgress()
                        view.showRecentCalls(t!!.CDRJSON)
                    }

                })


        compositeDisposable.add(disposable)

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

