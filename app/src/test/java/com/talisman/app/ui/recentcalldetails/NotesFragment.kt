package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by tarun on 11/10/17.
 */
class NotesFragment : Fragment() , View.OnClickListener
{

    private var isSendContainerVisible = false


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
       }
    }


}