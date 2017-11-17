package com.talisman.app.ui.forgotpassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.tarun.kotlin.isEmailValid
import com.example.tarun.talismanpi.R
import com.jakewharton.rxbinding.widget.RxTextView
import com.talisman.app.TalismanPiApplication
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Tarun on 11/13/17.
 */
class ForgotPasswordActivity : AppCompatActivity() ,ForgotPasswordContract.View , View.OnClickListener
{

    @Inject
     lateinit var forgotPasswordPresenter : ForgotPasswordPresenter

    private val compositeSubscription : CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        DaggerForgotPasswordComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .forgotPasswordModule(ForgotPasswordModule(this@ForgotPasswordActivity))
                .build().inject(this@ForgotPasswordActivity)

        initUi()
    }

    private fun initUi() {
        loginButton.setOnClickListener(this)

        val emalETDoneSub = RxTextView.editorActionEvents(email).subscribe { textViewEditorActionEvent ->
            if (textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtils.hideKeyboard(this@ForgotPasswordActivity, email)
                loginButton.callOnClick()
            }
        }

        compositeSubscription.add(emalETDoneSub)
    }

    override fun onBackPressed() {
        finish()
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        progressBar.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility=View.GONE
    }

    override fun onUnknownError(message: String, error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTimeout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNetworkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onClick(p0: View?) {
       when(p0!!.id){
           R.id.loginButton -> {
               KeyboardUtils.hideKeyboard(this@ForgotPasswordActivity, email)
               if (!isEmailValid(email.editableText.toString())) {
                   Toast.makeText(this, "Please check the email entered", Toast.LENGTH_SHORT).show()
                   return
               }

               forgotPasswordPresenter.forgotPassword(email.text.toString())
           }
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
        forgotPasswordPresenter.stop()
    }

    override fun successResponse(message: String) {
       Toast.makeText(this@ForgotPasswordActivity,message,Toast.LENGTH_LONG).show()
        finish()
    }



}