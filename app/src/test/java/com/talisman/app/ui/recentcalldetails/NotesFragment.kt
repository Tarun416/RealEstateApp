package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by tarun on 11/10/17.
 */
class NotesFragment : Fragment() , View.OnClickListener
{

    private var note1 : String=""
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
        initUi()
    }

    private fun initUi() {
        edit.setOnClickListener(this)
        send.setOnClickListener(this)

        if(!customerDetailResponse.description!!.value.isEmpty()) {
            previousNoteText.visibility=View.VISIBLE
            note.visibility=View.VISIBLE
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
               if(notesboxtext.text.isEmpty())
               {
                   Toast.makeText(activity,"Please write some note",Toast.LENGTH_LONG).show()
                   return
               }


           }
       }
    }


    fun setDetails(customerDetailResponse: CustomerDetailsResponse) {
        this.customerDetailResponse = customerDetailResponse
    }


}