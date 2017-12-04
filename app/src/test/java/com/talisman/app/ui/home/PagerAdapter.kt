package com.talisman.app.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.ArrayList


/**
 * Created by tarun on 11/9/17.
 */
class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment? {
        return  mFragmentList[position]
    }

    fun addFrag(fragment: Fragment) {
        mFragmentList.add(fragment) }


    override fun getCount(): Int {
        return mFragmentList.size
    }
}