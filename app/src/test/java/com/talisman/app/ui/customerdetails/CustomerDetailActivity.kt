package com.talisman.app.ui.customerdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.activity_customer_details.*
import kotlinx.android.synthetic.main.toolbar_customer_details.*

/**
 * Created by tarun on 11/10/17.
 */
class CustomerDetailActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)
        setSupportActionBar(toolbar)
        toolbarText.text = "Leads Info"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initUi()
    }

    private fun initUi() {
        cancel.visibility = View.GONE
        view.visibility = View.GONE
        done.text = getString(R.string.edit)
        firstName.setText("AMKCMD")
        toggleEditField(false)
        done.setOnClickListener(this)
        cancel.setOnClickListener(this)
    }

    private fun toggleEditField(b: Boolean) {
        firstName.isEnabled = b
        lastName.isEnabled = b
        phoneNumber.isEnabled = b
        address.isEnabled = b
        notes.isEnabled = b

        when {
            !b -> {
                cancel.visibility = View.GONE
                view.visibility = View.GONE
                done.text = getString(R.string.edit)
            }

            else -> {
                cancel.visibility = View.VISIBLE
                view.visibility = View.VISIBLE
                done.text = getString(R.string.done)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.done -> {
                when {
                    done.text.toString().equals("Edit", true) -> toggleEditField(true)
                }
            }

            R.id.cancel -> toggleEditField(false)
        }
    }

}