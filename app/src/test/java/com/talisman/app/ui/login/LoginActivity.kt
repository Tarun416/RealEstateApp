package com.talisman.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.tarun.kotlin.isEmailValid
import com.example.tarun.talismanpi.R
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding.widget.RxTextView
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.ui.forgotpassword.ForgotPasswordActivity
import com.talisman.app.ui.home.HomeActivity
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_login.*
import rx.subscriptions.CompositeSubscription
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.inject.Inject


/**
 * Created by tarun on 11/8/17.
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract.View {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    @Inject
    lateinit var preferences: TalismanPiPreferences

    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DaggerLoginComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .loginModule(LoginModule(this@LoginActivity))
                .build().inject(this@LoginActivity)


        initUi()
    }

    private fun initUi() {
        loginButton.setOnClickListener(this)
        forgotPassword.setOnClickListener(this)

        val pwdETDoneSub = RxTextView.editorActionEvents(password).subscribe { textViewEditorActionEvent ->
            if (textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtils.hideKeyboard(this@LoginActivity, password)
                loginButton.callOnClick()
            }
        }

        compositeSubscription.add(pwdETDoneSub)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.loginButton -> {
                KeyboardUtils.hideKeyboard(this@LoginActivity, password)
                if (!isEmailValid(email.editableText.toString())) {
                    Toast.makeText(this, "Please check the email entered", Toast.LENGTH_SHORT).show()
                    return
                } else if (password.text.toString().isEmpty()) {
                    Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }

                //Todo  use next line  when you want to send encrypted pwd

              //  loginPresenter.login(email.text.toString(), generatemd5(password.text.toString()))
                loginPresenter.login(email.text.toString(), password.text.toString())
            }

            R.id.forgotPassword -> startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))

        }

    }

    private fun generatemd5(s: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        Log.d("md5",BigInteger(1, md5.digest(s.toByteArray(Charset.defaultCharset()))).toString(16))
        return BigInteger(1, md5.digest(s.toByteArray(Charset.defaultCharset()))).toString(16)
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onUnknownError(message: String, error: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun onTimeout() {

    }

    override fun onNetworkError() {

    }

    override fun onFailure(errorMsg: String) {

    }

    override fun onConnectionError() {

    }

    override fun login() {
        preferences.loginDone = true

        if(FirebaseInstanceId.getInstance().token!=null) {
            preferences.registrationToken = FirebaseInstanceId.getInstance().token
            loginPresenter.sendRegistrationToken()
        }

        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
        loginPresenter.onStop()
    }

    override fun invalidCredentialError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDeviceRegistered() {
      Toast.makeText(this@LoginActivity,"Device registered on server",Toast.LENGTH_LONG).show()
    }


}