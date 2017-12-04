package com.talisman.app.ui.recentcalldetails.ticketdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.ticketdetails.DaggerTicketDetailsComponent
import com.talisman.app.ui.ticketdetails.TicketDetailsContract
import com.talisman.app.ui.ticketdetails.TicketDetailsModule
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_ticket_create.*
import kotlinx.android.synthetic.main.toolbar_customer_details.*
import javax.inject.Inject

/**
 * Created by Tarun on 12/4/17.
 */
class TicketCreateActivity : AppCompatActivity() , View.OnClickListener , TicketDetailsContract.View {

    @Inject
    lateinit var presenter : TicketCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_create)
        setSupportActionBar(toolbar)


        DaggerTicketDetailsComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .ticketDetailsModule(TicketDetailsModule(this@TicketCreateActivity))
                .build().inject(this@TicketCreateActivity)

        initUi()
    }

    private fun initUi() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        done.text="Save"
        cancel.visibility=View.GONE
        view.visibility=View.GONE
        toolbarText.text="Create Ticket"
        done.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.done -> {
                if(description.text.isEmpty())
                {
                    Toast.makeText(this@TicketCreateActivity,"Description can't be empty",Toast.LENGTH_LONG).show()
                    return
                }
                else
                {
                    KeyboardUtils.hideKeyboard(this@TicketCreateActivity,description)
                    presenter.crmCreateLogin(status.selectedItem.toString(),
                            priority.selectedItem.toString(),
                            description.text.toString(),
                            resolution.text.toString(),
                            ticketName.text.toString(),intent.extras.getString("phoneNumber"))
                }
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

    override fun onFailure(errorMsg: String) {

    }

    override fun onConnectionError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

    override fun updateSuccess() {
        Toast.makeText(this,"Ticket Created",Toast.LENGTH_LONG).show()
        finish()
    }



}