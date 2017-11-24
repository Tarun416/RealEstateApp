package com.talisman.app

import com.example.tarun.talismanpi.BuildConfig
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by Tarun on 11/21/17.
 */
class RetrofitClient {

    companion object {
        lateinit var retrofit: Retrofit


        private lateinit var application: TalismanPiApplication

        fun getClient(baseUrl: String, application: TalismanPiApplication): Retrofit {
            this.application = application
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()

            return retrofit
        }


        private fun provideOkHttpClient(cache: Cache = provideOkHttpCache(application)): OkHttpClient {
            // create an instance of OkLogInterceptor using a builder()
            val okLogInterceptor = OkLogInterceptor.builder()
                    .setLogInterceptor { url ->
                        Timber.tag("API_Retro").d(" " + url)
                        true
                    }.withAllLogData().build()
            val client = OkHttpClient.Builder()
            client.connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS) // //TODO Code review : set this to 30
            client.addInterceptor { mChain ->
                val original = mChain.request()
                val builder = original.newBuilder().header("Content-Type", "application/json")
                val request = builder.build()
                mChain.proceed(request)
            }
            if (BuildConfig.DEBUG)
                client
                        .addInterceptor(ChuckInterceptor(application.applicationContext))
                        .addInterceptor(okLogInterceptor)// add OkLogInterceptor to OkHttpClient's application interceptors
                        .cache(cache)
            else
                client
                        .cache(cache)

            return client.build()
        }


        private fun provideOkHttpCache(application: TalismanPiApplication): Cache {
            val cacheSize = 10 * 1024 * 1024 // 10 MiB
            return Cache(application.cacheDir, cacheSize.toLong())
        }



    }
}