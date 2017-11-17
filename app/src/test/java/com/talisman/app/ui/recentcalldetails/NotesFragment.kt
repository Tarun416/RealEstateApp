package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R

/**
 * Created by tarun on 11/10/17.
 */
class NotesFragment : Fragment()
{
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

}