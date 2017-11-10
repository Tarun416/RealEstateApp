package com.talisman.app.ui.drawer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.mindorks.placeholderview.annotations.Click
import android.widget.TextView
import com.example.tarun.talismanpi.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View


/**
 * Created by varun on 11/8/17.
 */
@Layout(R.layout.drawer_item)
class DrawerMenuItem(private val mContext: Context, private val mMenuPosition: Int,private var mCallBack: DrawerCallBack) {
    //private var mCallBack: DrawerCallBack? = null

    @View(R.id.itemNameTxt)
    private val itemNameTxt: TextView? = null

    @View(R.id.itemIcon)
    private val itemIcon: ImageView? = null

    @Resolve
    private fun onResolved() {
        when (mMenuPosition) {
            DRAWER_MENU_ITEM_HOME -> {
                itemNameTxt!!.text = "Home"
                itemIcon!!.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_home))
            }
            DRAWER_MENU_ITEM_PROFILE -> {
                itemNameTxt!!.text = "Profile"
                itemIcon!!.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_person))
            }
            DRAWER_MENU_ITEM_SETTINGS -> {
                itemNameTxt!!.text = "Settings"
                itemIcon!!.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_settings))
            }
            DRAWER_MENU_ITEM_ABOUT -> {
                itemNameTxt!!.text = "About"
                itemIcon!!.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.app_icon))
            }
            DRAWER_MENU_ITEM_LOGOUT -> {
                itemNameTxt!!.text = "LogOut"
                itemIcon!!.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.logout))
            }

        }
    }

    @Click(R.id.mainView)
    private fun onMenuItemClick() {
        when (mMenuPosition) {

            DRAWER_MENU_ITEM_HOME -> {
                if (mCallBack != null) mCallBack!!.onHomeMenuSelected()
            }

            DRAWER_MENU_ITEM_PROFILE -> {
                if (mCallBack != null) mCallBack!!.onProfileMenuSelected()
            }

            DRAWER_MENU_ITEM_SETTINGS -> {
                if (mCallBack != null) mCallBack!!.onSettingsMenuSelected()
            }
            DRAWER_MENU_ITEM_ABOUT -> {
                if (mCallBack != null) mCallBack!!.onAboutMenuSelected()
            }
            DRAWER_MENU_ITEM_LOGOUT -> {
                if (mCallBack != null) mCallBack!!.onLogoutMenuSelected()
            }

        }
    }


    fun setDrawerCallBack(callBack: DrawerCallBack) {
        mCallBack = callBack
    }

    interface DrawerCallBack {
        fun onHomeMenuSelected()
        fun onProfileMenuSelected()
        fun onSettingsMenuSelected()
        fun onAboutMenuSelected()
        fun onLogoutMenuSelected()
    }

    companion object {
        val DRAWER_MENU_ITEM_HOME = 1
        val DRAWER_MENU_ITEM_PROFILE = 2
        val DRAWER_MENU_ITEM_SETTINGS = 3
        val DRAWER_MENU_ITEM_ABOUT = 4
        val DRAWER_MENU_ITEM_LOGOUT = 5
    }
}