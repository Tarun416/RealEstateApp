package com.talisman.app.ui.login

import com.talisman.app.ApiInterface
import com.talisman.app.CallbackWrapper
import com.talisman.app.Constants
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.ui.login.model.LogInResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Tarun on 11/16/17.
 */
class LoginPresenter
@Inject
constructor(private var retrofit: Retrofit,
            var view: LoginContract.View) : LoginContract.Presenter {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val preferences : TalismanPiPreferences = TalismanPiPreferences()

    override fun login(email: String, password: String) {
        view.showProgress()
        val hashmap: HashMap<String, String> = HashMap()
        hashmap.put(Constants.EMAIL_ID, email)
        hashmap.put(Constants.PASSWORD, password)
        hashmap.put(Constants.DEVICE_ID, "")
        hashmap.put(Constants.OS_VERSION, "")

        val disposable = retrofit.create<ApiInterface>(ApiInterface::class.java).login(hashmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<LogInResponse>(view) {
                    override fun onSuccess(t: LogInResponse) {
                        view.hideProgress()
                        if(t.data!=null ) {
                            preferences.agentNo = t.data.User.mobile
                            preferences.referenceNo = t.data.Business.reference_number
                            preferences.crmbusinessid = t.data.Business.crm_business_id
                            preferences.status = t.data.User.login_status
                            preferences.userId=t.data.User.id
                            preferences.businessId=t.data.Business.id
                            view.login()
                        }
                        else
                        {
                            if(t.error!=null)
                            {
                                view.invalidCredentialError(t.error)
                            }
                        }
                    }

                })


        compositeDisposable.add(disposable)

    }

    override fun sendRegistrationToken() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.put("user_id", preferences!!.userId!!)
        hashMap.put("fcm_id", preferences.registrationToken!!)


        retrofit.create(ApiInterface::class.java).updateFCMDetails(hashMap)
                // computation
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeWith(object  : DisposableSubscriber<Any>(){
                    override fun onComplete() {

                    }

                    override fun onError(t: Throwable?) {

                    }

                    override fun onNext(t: Any?) {
                        view.onDeviceRegistered()

                    }
                })
    }

    fun onStop() {
        compositeDisposable.clear()
    }


}


