package com.talisman.app.ui.recentcalldetails.customerdetails

import android.util.Log
import com.talisman.app.ApiInterface
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Tarun on 11/23/17.
 */
class CustomerDetailsPresenter
@Inject
        constructor(val retrofit : Retrofit , val view : CustomerDetailsContract.View) : CustomerDetailsContract.Presenter{

    private val compositeDisposable = CompositeDisposable()


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
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: CustomerDetailsResponse?) {
                        view.hideProgress()
                        if(t!!.phone_mobile!=null && t!!.phone_mobile!!.value!=null)
                        view.passCustomerDetails(t!!)
                        else
                            view.showErrorMesssage("prospect number not found")
                    }

                })


        compositeDisposable.add(disposable1)
    }

    fun onStop() {
        compositeDisposable.clear()
    }
}