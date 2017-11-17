package com.talisman.app.ui.login

import com.talisman.app.ApiInterface
import com.talisman.app.CallbackWrapper
import com.talisman.app.Constants
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.ui.login.model.LogInResponse
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Tarun on 11/16/17.
 */
class LoginPresenter

@Inject
constructor( var retrofit : Retrofit,
             var preferences : TalismanPiPreferences,
             var view : LoginContract.View) : LoginContract.Presenter {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun login(email : String, password : String) {
          view.showProgress()
          val hashmap : HashMap<String,String> = HashMap()
          hashmap.put(Constants.EMAIL_ID,email)
          hashmap.put(Constants.PASSWORD,password)
          hashmap.put(Constants.DEVICE_ID,"")
          hashmap.put(Constants.OS_VERSION,"")

          val disposable = retrofit.create<ApiInterface>(ApiInterface::class.java).login(hashmap)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(object : CallbackWrapper<LogInResponse>(view)
                  {
                      override fun onSuccess(t: LogInResponse) {
                        view.hideProgress()
                          view.login()
                      }

                  })


        compositeDisposable.add(disposable)

    }

    fun onStop() {
        compositeDisposable.clear()
    }


}


