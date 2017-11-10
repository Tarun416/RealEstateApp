package com.talisman.app

import com.example.tarun.talismanpi.BuildConfig
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by varun on 11/8/17.
 */
@Module
class NetModule(baseUrl: String,application: TalismanPiApplication) {


    val baseUrl = baseUrl
    val application = application


    // Dagger will only look for methods annotated with @Provides

    @Provides
    @Singleton
    fun provideOkHttpCache(application: TalismanPiApplication): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(application.cacheDir, cacheSize.toLong())
        return cache
    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    fun providesSharedPreferences():TalismanPiPreferences {
        // Application reference must come from AppModule.class
        return TalismanPiPreferences()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
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

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        Timber.tag("OKHTTPCOnfig").d("Okhttp = " + okHttpClient.connectTimeoutMillis())
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton // //TODO Code review :  not to be done here , not required since database instance is already singleton accessd by App.database
    fun providesDatabase(app: TalismanPiApplication): AppDataBase = app.getDatabase()

}
