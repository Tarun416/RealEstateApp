package com.talisman.app.ui.settings

import com.talisman.app.ApiInterface
import com.talisman.app.CallbackWrapper
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.ui.settings.model.SettingsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Tarun on 11/27/17.
 */
class SettingsPresenter
@Inject
constructor(var retrofit: Retrofit, val view: SettingsContract.View) : SettingsContract.Presenter {

    private val preferences = TalismanPiPreferences()
    private val compositeDisposable = CompositeDisposable()

    override fun setStatus(status: String) {
        view.showProgress()

        val hashMap: HashMap<String, String> = HashMap()
        hashMap.put("status", status)
        hashMap.put("userId", preferences.userId!!)
        hashMap.put("businessId", preferences.businessId!!)
        hashMap.put("business_ref", preferences.referenceNo!!)
        hashMap.put("mobile", preferences.agentNo!!)

        val disposable  = retrofit.create(ApiInterface::class.java).updateLoginStatus(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : CallbackWrapper<SettingsResponse>(view){
                    override fun onSuccess(t: SettingsResponse) {
                        view.hideProgress()
                        if(t.error.equals("success",true))
                        {
                            view.showSuccessMessage()
                            preferences.status=status

                        }
                        else
                        {
                            view.failed()
                        }
                    }

                })


    }


    fun onStop()
    {
        compositeDisposable.clear()
    }

}