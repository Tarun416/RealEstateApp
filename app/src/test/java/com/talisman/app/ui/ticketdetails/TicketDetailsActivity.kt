package com.talisman.app.ui.ticketdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_customer_details.*

/**
 * Created by tarun on 11/10/17.
 */
class TicketDetailsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)
        setSupportActionBar(toolbar)
        toolbarText.text="Ticket Info"
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