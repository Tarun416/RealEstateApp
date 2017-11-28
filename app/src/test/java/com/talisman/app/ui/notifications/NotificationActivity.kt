package com.talisman.app.ui.notifications

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_home.*

/**
 * Created by tarun on 11/10/17.
 */
class NotificationActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        setSupportActionBar(toolbar)
        toolbarText.text="Notifications"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
       finish()
    }


}