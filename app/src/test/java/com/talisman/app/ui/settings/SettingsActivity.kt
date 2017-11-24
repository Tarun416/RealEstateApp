package com.talisman.app.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_home.*

/**
 * Created by Tarun on 11/17/17.
 */
class SettingsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        toolbarText.text="Settings"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }

}