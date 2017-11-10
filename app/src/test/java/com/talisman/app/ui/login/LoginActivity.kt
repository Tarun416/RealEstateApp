package com.talisman.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by varun on 11/8/17.
 */
class LoginActivity : AppCompatActivity() , View.OnClickListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
       when(p0!!.id)
       {
           R.id.loginButton -> {
               val intent = Intent(this@LoginActivity, HomeActivity::class.java)
               startActivity(intent)
           }

       }

    }


}