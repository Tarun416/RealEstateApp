package com.talisman.app.ui.forgotpassword

import com.talisman.app.ApiInterface
import com.talisman.app.CallbackWrapper
import com.talisman.app.Constants
import com.talisman.app.ui.forgotpassword.model.ForgotPasswordResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Tarun on 11/17/17.
 */
class ForgotPasswordPresenter

@Inject
constructor( val retrofit : Retrofit,
             val view : ForgotPasswordContract.View) : ForgotPasswordContract.Presenter{

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun forgotPassword(email: String) {

        view.showProgress()
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.put(Constants.EMAIL_ID, email)

        val disposable = retrofit.create<ApiInterface>(ApiInterface::class.java).forgotPassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<ForgotPasswordResponse>(view) {
                    override fun onSuccess(t: ForgotPasswordResponse) {
                        view.hideProgress()
                          view.successResponse(t.data.msg)
                    }

                })

        compositeDisposable.add(disposable)

    }

    fun stop()
    {
        compositeDisposable.clear()
    }

}
