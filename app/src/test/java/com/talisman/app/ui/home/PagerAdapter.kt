package com.talisman.app.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.talisman.app.ui.customers.CustomerFragment
import com.talisman.app.ui.recentcalls.RecentCallFragment
import com.talisman.app.ui.tickets.TicketFragment


/**
 * Created by varun on 11/9/17.
 */
class PagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                RecentCallFragment()
            }
            1 -> {
                CustomerFragment()
            }
            2 -> {
                TicketFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}