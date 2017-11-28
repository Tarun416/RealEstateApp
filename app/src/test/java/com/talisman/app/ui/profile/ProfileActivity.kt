package com.talisman.app.ui.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiPreferences
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar_customer_details.*

/**
 * Created by tarun on 11/10/17.
 */
class ProfileActivity : AppCompatActivity() {
    private val preferences = TalismanPiPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        initUi()
    }

    private fun initUi() {
        toolbarText.text = "Profile"
        cancel.visibility = View.GONE
        done.visibility = View.GONE
        view.visibility = View.GONE
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        agentName.text = preferences.agentName
        userName.text = preferences.userName
        mobileNumber.text = preferences.agentNo
    }

    override fun onBackPressed() {
        finish()
    }

}