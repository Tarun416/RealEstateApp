package com.talisman.app.ui.recentcalldetails

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.talisman.app.ui.recentcalldetails.customerdetails.CustomerDetailsFragment

/**
 * Created by tarun on 11/10/17.
 */
class PagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                CustomerDetailsFragment()
            }
            1 -> {
                NotesFragment()
            }
            2 -> {
                TicketFragment()
            }
            3 -> {
                CallHistoryFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}