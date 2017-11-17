package com.talisman.app.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.login.LoginActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * Created by tarun on 11/8/17.
 */
class SplashActivity : AppCompatActivity()
{

    private val disposables : CompositeDisposable= CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val disposable= Flowable.interval(2000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val loginIntent= Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(loginIntent)
                    disposables.clear()
                    finish()
                }

        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }




}