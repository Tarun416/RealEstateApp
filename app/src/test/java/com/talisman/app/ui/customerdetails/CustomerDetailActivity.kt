package com.talisman.app.ui.customerdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_customer_details.*

/**
 * Created by tarun on 11/10/17.
 */
class CustomerDetailActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)
        setSupportActionBar(toolbar)
        toolbarText.text="Leads Info"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
         finish()
    }


}