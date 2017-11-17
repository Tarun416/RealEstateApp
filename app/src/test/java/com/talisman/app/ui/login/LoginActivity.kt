package com.talisman.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.tarun.kotlin.isEmailValid
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.forgotpassword.ForgotPasswordActivity
import com.talisman.app.ui.home.HomeActivity
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by tarun on 11/8/17.
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract.View {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DaggerLoginComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .loginModule(LoginModule(this@LoginActivity))
                .build().inject(this@LoginActivity)

        loginButton.setOnClickListener(this)
        forgotPassword.setOnClickListener(this)
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

                loginPresenter.login(email.text.toString(), password.text.toString())

            }

            R.id.forgotPassword -> startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))

        }

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
          Toast.makeText(this@LoginActivity,message,Toast.LENGTH_LONG).show()
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
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.onStop()
    }


}