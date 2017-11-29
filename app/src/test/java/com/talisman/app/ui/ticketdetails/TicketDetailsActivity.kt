package com.talisman.app.ui.ticketdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import kotlinx.android.synthetic.main.activity_ticket_details.*
import kotlinx.android.synthetic.main.toolbar_customer_details.*
import javax.inject.Inject


/**
 * Created by tarun on 11/10/17.
 */
class TicketDetailsActivity : AppCompatActivity(), View.OnClickListener , TicketDetailsContract.View{

    @Inject
    lateinit var presenter : TicketDetailsPresenter

    private lateinit var ticketId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)
        setSupportActionBar(toolbar)

        DaggerTicketDetailsComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .ticketDetailsModule(TicketDetailsModule(this@TicketDetailsActivity))
                .build().inject(this@TicketDetailsActivity)

        initUi()
    }

    private fun initUi() {
        toolbarText.text = "Ticket Info"
        cancel.visibility = View.GONE
        done.text = "EDIT"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        done.setOnClickListener(this)
        cancel.setOnClickListener(this)
        view.visibility = View.GONE
        status.isEnabled = false
        priority.isEnabled = false
        setDataFromIntent()
    }


    private fun setDataFromIntent() {

        ticketNumber.text = intent.extras.getString("ticketNumber")
        when {
            intent.extras.getString("status").equals("New", true) -> status.setSelection(1)
            intent.extras.getString("status").equals("Open", true) -> status.setSelection(0)
            intent.extras.getString("status").equals("Closed", true) -> status.setSelection(2)
            intent.extras.getString("status").equals("clear", true) -> status.setSelection(3)
        }

        when {
            intent.extras.getString("priority").equals("High", true) -> priority.setSelection(0)
            intent.extras.getString("priority").equals("Medium", true) -> priority.setSelection(1)
            intent.extras.getString("priority").equals("Low", true) -> priority.setSelection(2)
        }

        ticketId = intent.extras.getString("ticketId")

        description.setText(intent.extras.getString("description"))
        resolution.setText(intent.extras.getString("resolution"))
    }


    override fun onBackPressed() {
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.done -> {
                if (done.text.toString().equals("edit", true))
                    toggleViews(true)
                else {

                    // do validation
                    if (description.text.isEmpty()) {
                        Toast.makeText(this, "Give some description", Toast.LENGTH_LONG).show()
                        return
                    }
                    else{
                        // call update api
                        presenter.crmLogin(status.selectedItem.toString(),
                                priority.selectedItem.toString(),
                                description.text.toString(),
                                resolution.text.toString(),
                                ticketId)
                    }
                }
            }
            R.id.cancel -> {
                toggleViews(false)
                setDataFromIntent()
            }
        }
    }

    private fun toggleViews(b: Boolean) {
        status.isEnabled = b
        priority.isEnabled = b
        description.isEnabled = b
        resolution.isEnabled = b
        if (b) {
            done.text = "Done"
            view.visibility = View.VISIBLE
            cancel.visibility = View.VISIBLE
        } else {
            done.text = "Edit"
            view.visibility = View.GONE
            cancel.visibility = View.GONE
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
       Toast.makeText(this,"Ticket updated",Toast.LENGTH_LONG).show()
        finish()
    }



}