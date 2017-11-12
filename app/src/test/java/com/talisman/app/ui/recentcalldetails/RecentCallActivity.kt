package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar_home.*
import android.view.LayoutInflater
import android.widget.TextView
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.activity_recent_calls.*


/**
 * Created by varun on 11/9/17.
 */
class RecentCallActivity : AppCompatActivity() {

    private lateinit var tabOne: TextView
    private lateinit var tabTwo: TextView
    private lateinit var tabThree: TextView
    private lateinit var tabFour: TextView

    private lateinit var pagerAdapter: PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_calls)
        setSupportActionBar(toolbar)
        toolbarText.text = "Recent Calls"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpTabIcons()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setUpTabIcons() {

        pagerAdapter = PagerAdapter(supportFragmentManager, 4)
        pager.adapter = pagerAdapter

        tabs.setupWithViewPager(pager)

        tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "Schedule"
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.green_schedule_icon, 0, 0)
        tabs.getTabAt(0)!!.customView = tabOne

        tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "Notes"
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.green_notes_icon, 0, 0)
        tabs.getTabAt(1)!!.customView = tabTwo

        tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = "SMS"
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.green_sms_icon, 0, 0)
        tabs.getTabAt(2)!!.customView = tabThree

        tabFour = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabFour.text = "Call Back"
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.green_call_back_icon, 0, 0)
        tabs.getTabAt(3)!!.customView = tabFour

    }


}