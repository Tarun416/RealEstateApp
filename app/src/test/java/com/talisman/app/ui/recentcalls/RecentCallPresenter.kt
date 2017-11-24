package com.talisman.app.ui.recentcalls

import com.example.tarun.talismanpi.BuildConfig
import com.talisman.app.*
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Tarun on 11/21/17.
 */
class RecentCallPresenter
@Inject constructor(var view: RecentCallContract.View ) : RecentCallContract.Presenter {

    private val preferences : TalismanPiPreferences = TalismanPiPreferences()
    private lateinit var apiInterface : ApiInterface
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()


    override fun getRecentCalls() {
          view.showProgress()
          apiInterface=ApiUtils.getApiService(BuildConfig.CDR_SERVER_URL, TalismanPiApplication.instance)
          val disposable =/*Flowable.interval(2000,TimeUnit.MILLISECONDS).flatMap { */  apiInterface.getRecentCalls(Constants.CONTENT_TYPE,Constants.DEVELOPER_ID,"20170806110000",
                  "20170831180000",  preferences.actualNumber!!,Constants.APP_TYPE)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(object : DisposableSubscriber<RecentCallResponse>(){

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


    fun onStop()
    {
        compositeDisposable.clear()
    }
}

