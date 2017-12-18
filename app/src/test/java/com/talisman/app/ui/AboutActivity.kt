package com.talisman.app.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.toolbar_home.*
import android.content.pm.PackageManager
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Created by Tarun on 12/18/17.
 */
class AboutActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        toolbarText.text="About"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }


        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            versionName.text=pInfo.versionName
            versionCode.text=pInfo.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


    }

}