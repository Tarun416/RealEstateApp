package com.talisman.app.ui.forgotpassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tarun.talismanpi.R

/**
 * Created by Tarun on 11/13/17.
 */
class ForgotPasswordActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    override fun onBackPressed() {
        finish()
    }


}