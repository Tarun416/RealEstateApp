package com.talisman.app

import com.example.tarun.talismanpi.BuildConfig
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by Tarun on 11/28/17.
 */
class FCMIDService : FirebaseInstanceIdService() {

    internal var preferences: TalismanPiPreferences? = null

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Timber.tag("FCM").d(" " + "token  = " + refreshedToken)
        preferences = TalismanPiPreferences()
        preferences?.registrationToken = refreshedToken
        val client = OkHttpClient.Builder()
        val okHttpClient = client.addInterceptor { mChain ->
            val original = mChain.request()
            val builder = original.newBuilder().header("Content-Type", "application/json")
            val request = builder.build()
            mChain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
                .build()

        if (preferences?.userId != null) {

            val hashMap: HashMap<String, String> = HashMap()
            hashMap.put("user_id", preferences!!.userId!!)
            hashMap.put("fcm_id", refreshedToken!!)




            retrofit.create(ApiInterface::class.java).updateFCMDetails(hashMap)
                    // computation
                    .subscribeOn(Schedulers.computation())
                    // new thread
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribeWith(object : DisposableSubscriber<Any>() {
                        override fun onComplete() {

                        }

                        override fun onError(t: Throwable?) {

                        }

                        override fun onNext(t: Any?) {

                        }
                    })
        }


        /*retrofit.create(ApiInterface::class.java).login(token, preferences!!.username!!, preferences!!.userEncryptionPassword!!, refreshedToken, "android")
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .onErrorResumeNext({ Observable.error(it) })
                .subscribe(object : Subscriber<SignInResponse>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {}

                    override fun onNext(response: SignInResponse) {}
                })*/

    }


    companion object {
        private val TAG = "FirebaseRegistration"
    }
}
