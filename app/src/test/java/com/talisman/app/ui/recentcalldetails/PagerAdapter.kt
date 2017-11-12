package com.talisman.app.ui.recentcalldetails

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by varun on 11/10/17.
 */
class PagerAdapter(fm: FragmentManager, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                ScheduleFragment()
            }
            1 -> {
                NotesFragment()
            }
            2 -> {
                SMSFragment()
            }
            3 -> {
                CallBackFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}