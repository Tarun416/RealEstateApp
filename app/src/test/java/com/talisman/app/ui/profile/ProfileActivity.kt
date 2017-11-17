package com.talisman.app.ui.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_customer_details.*

/**
 * Created by tarun on 11/10/17.
 */
class ProfileActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        toolbarText.text="Profile"
        cancel.visibility= View.GONE
        done.text="EDIT"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }

}