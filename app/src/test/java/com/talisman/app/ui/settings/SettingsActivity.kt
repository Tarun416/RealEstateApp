package com.talisman.app.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.tarun.kotlin.isOnline
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.TalismanPiPreferences
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar_customer_details.*
import vn.luongvo.widget.iosswitchview.SwitchView
import javax.inject.Inject

/**
 * Created by Tarun on 11/17/17.
 */
class SettingsActivity : AppCompatActivity() , View.OnClickListener , SwitchView.OnCheckedChangeListener , SettingsContract.View
{
    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    private var isSendContainerVisible = false

    private val preferences = TalismanPiPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        DaggerSettingsComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .settingsModule(SettingsModule(this@SettingsActivity))
                .build().inject(this@SettingsActivity)

        initUi()
    }

    private fun initUi() {
        cancel.visibility=View.GONE
        view.visibility=View.GONE
        done.visibility=View.GONE
        done.text="Save"
        toolbarText.text="Settings"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        edit.setOnClickListener(this)
        done.setOnClickListener(this)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        switchview.isChecked = preferences.status.equals("Ready",true)
        switchview.setOnCheckedChangeListener(this)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onClick(p0: View?) {

        KeyboardUtils.hideKeyboard(this@SettingsActivity,newPassword)
        when(p0!!.id)
        {
            R.id.edit -> when {
                isSendContainerVisible -> {
                    sendContainer.visibility = View.GONE
                    done.visibility=View.GONE
                    isSendContainerVisible=false
                }
                else -> {
                    sendContainer.visibility = View.VISIBLE
                    isSendContainerVisible = true
                    done.visibility=View.VISIBLE
                }
            }

            R.id.done -> {
                   if(currentPassword.text.isEmpty() || newPassword.text.isEmpty())
                   {
                       Toast.makeText(this@SettingsActivity,"None of the field can be empty",Toast.LENGTH_LONG).show()
                       return
                   }


                if(isOnline(this))
                  settingsPresenter.changePassword(currentPassword.text.toString(),newPassword.text.toString())
                else
                    Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onCheckedChanged(p0: SwitchView?, p1: Boolean) {
       if(p1)
       {
           if(isOnline(this)) {
               settingsPresenter.setStatus("ready")
           }
           else {
               Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
               switchview.isChecked = preferences.status.equals("Ready",true)
           }
       }
        else
       {
           if(isOnline(this)) {
               settingsPresenter.setStatus("busy")
           }
           else
           {
               switchview.isChecked = preferences.status.equals("Ready",true)
               Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()
           }

       }
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility=View.GONE
    }

    override fun onUnknownError(message: String, error: String) {

    }

    override fun onTimeout() {

    }

    override fun onNetworkError() {

    }

    override fun onFailure(message: String) {
        Toast.makeText(this@SettingsActivity,message,Toast.LENGTH_LONG).show()
    }

    override fun onConnectionError() {
    }

    override fun onDestroy() {
        super.onDestroy()
        settingsPresenter.onStop()
    }

    override fun showSuccessMessage() {
        Toast.makeText(this,"Login status changed",Toast.LENGTH_LONG).show()
    }

    override fun failed() {
        Toast.makeText(this,"Failed to perform",Toast.LENGTH_LONG).show()
        switchview.setOnCheckedChangeListener(null)
        switchview.isChecked = preferences.status.equals("Ready",true)
        switchview.setOnCheckedChangeListener(this)

    }

    override fun showSuccessMessage(message: String) {
        Toast.makeText(this@SettingsActivity,message,Toast.LENGTH_LONG).show()
        currentPassword.setText("")
        newPassword.setText("")
        sendContainer.visibility=View.GONE
    }


}