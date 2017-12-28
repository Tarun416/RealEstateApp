package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.createcustomer.CreateCustomerContract
import com.talisman.app.ui.createcustomer.CreateCustomerModule
import com.talisman.app.ui.createcustomer.CreateCustomerPresenter
import com.talisman.app.ui.createcustomer.DaggerCreateCustomerComponent
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_notes.*
import javax.inject.Inject

/**
 * Created by tarun on 11/10/17.
 */
class NotesFragment : Fragment() , View.OnClickListener, CreateCustomerContract.View
{

    @Inject
    lateinit var presenter: CreateCustomerPresenter

    private var isSendContainerVisible = false
    private lateinit var customerDetailResponse : CustomerDetailsResponse

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): NotesFragment {
            val fragment = NotesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_notes,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerCreateCustomerComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .createCustomerModule(CreateCustomerModule(this@NotesFragment))
                .build().inject(this@NotesFragment)

        initUi()
    }

    private fun initUi() {
        edit.setOnClickListener(this)
        send.setOnClickListener(this)

        if(!customerDetailResponse.description!!.value.isEmpty()) {
            previousNoteText.visibility=View.VISIBLE
            note.visibility=View.VISIBLE
            if(note.text.isEmpty())
            note.text = customerDetailResponse.description!!.value
        }
        else
        {
            previousNoteText.visibility=View.GONE
            note.visibility=View.GONE
        }
    }

    override fun onClick(p0: View?) {
       when(p0!!.id)
       {
           R.id.edit -> when {
               isSendContainerVisible -> {
                   sendContainer.visibility = View.GONE
                   isSendContainerVisible=false
               }
               else -> {
                   sendContainer.visibility = View.VISIBLE
                   isSendContainerVisible = true
               }
           }

           R.id.send -> {
               KeyboardUtils.hideKeyboard(activity,notesboxtext)
               if(notesboxtext.text.isEmpty())
               {
                   Toast.makeText(activity,"Please write some note",Toast.LENGTH_LONG).show()
                   return
               }

               presenter.crmLogin("","","","","","","","", customerDetailResponse.id!!.value,notesboxtext.text.toString(),"")

           }
       }
    }


    fun setDetails(customerDetailResponse: CustomerDetailsResponse) {
        this.customerDetailResponse = customerDetailResponse
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
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

    override fun onSuccess() {
       Toast.makeText(activity,"Note created",Toast.LENGTH_LONG).show()
        sendContainer.visibility=View.GONE
        previousNoteText.visibility=View.VISIBLE
        note.visibility=View.VISIBLE
        note.text = notesboxtext.text.toString()
        notesboxtext.setText("")
        customerDetailResponse.description!!.value=note.text.toString()
    }


}