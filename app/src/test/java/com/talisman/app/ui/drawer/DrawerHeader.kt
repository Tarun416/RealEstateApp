package com.talisman.app.ui.drawer

import android.widget.ImageView
import android.widget.TextView
import com.example.tarun.talismanpi.R
import com.example.tarun.talismanpi.R.layout.drawer_header
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View


/**
 * Created by tarun on 11/8/17.
 */
@NonReusable
@Layout(R.layout.drawer_header)
class DrawerHeader(var imgUrl: String, var name: String, var phone: String) {

    @View(R.id.img_profile)
    private val profileImage: ImageView? = null

    @View(R.id.name)
    private val nameTxt: TextView? = null

    @View(R.id.phone)
    private val emailTxt: TextView? = null

    @Resolve
    private fun onResolved() {
        nameTxt!!.text = name
        emailTxt!!.text = phone
    }
}