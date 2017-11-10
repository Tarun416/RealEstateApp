package com.talisman.app.ui.home

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.example.tarun.talismanpi.R

import com.talisman.app.ui.drawer.DrawerHeader
import com.talisman.app.ui.drawer.DrawerMenuItem
import com.talisman.app.ui.notifications.NotificationActivity
import com.talisman.app.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_home.*

/**
 * Created by varun on 11/8/17.
 */
class HomeActivity : AppCompatActivity(), DrawerMenuItem.DrawerCallBack {

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        toolbarText.text = getString(R.string.appName)
        toolbarText.setTypeface(null, Typeface.BOLD)
        setUpDrawer()
        setViewPager()
    }

    private fun setUpDrawer() {
        drawerView.addView(DrawerHeader("", "Ananya", "2232323232"))
                .addView(DrawerMenuItem(this.applicationContext, DrawerMenuItem.DRAWER_MENU_ITEM_HOME, this))
                .addView(DrawerMenuItem(this.applicationContext, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, this))
                .addView(DrawerMenuItem(this.applicationContext, DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS, this))
                .addView(DrawerMenuItem(this.applicationContext, DrawerMenuItem.DRAWER_MENU_ITEM_ABOUT, this))
                .addView(DrawerMenuItem(this.applicationContext, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, this))

        val drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onHomeMenuSelected() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    override fun onProfileMenuSelected() {
        drawerLayout.closeDrawer(Gravity.START)
        startActivity(Intent(this@HomeActivity,ProfileActivity::class.java))
    }

    override fun onSettingsMenuSelected() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    override fun onAboutMenuSelected() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    override fun onLogoutMenuSelected() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    private fun setViewPager() {
        tab_layout.addTab( tab_layout.newTab().setText(getString(R.string.recent_calls)))
        tab_layout.addTab( tab_layout.newTab().setText(getString(R.string.customers)))
        tab_layout.addTab( tab_layout.newTab().setText(getString(R.string.tickets)))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        pagerAdapter= PagerAdapter(supportFragmentManager,tab_layout.tabCount)
        pager.adapter=pagerAdapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            R.id.notificationIcon -> startActivity(Intent(this@HomeActivity,NotificationActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }



}