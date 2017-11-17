package com.talisman.app.ui.tickets

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.ticketdetails.TicketDetailsActivity
import kotlinx.android.synthetic.main.fragment_tickets.*

/**
 * Created by tarun on 11/9/17.
 */
class TicketFragment : Fragment(), TicketAdapter.OnTicketClick {

    private lateinit var ticketAdapter: TicketAdapter

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): TicketFragment {
            val fragment = TicketFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tickets, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        ticketRecyclerView.layoutManager = LinearLayoutManager(activity)
        ticketRecyclerView.setHasFixedSize(true)
        ticketAdapter = TicketAdapter(activity, this@TicketFragment)
        ticketRecyclerView.adapter = ticketAdapter
    }

    override fun onTicketClick() {
        startActivity(Intent(activity, TicketDetailsActivity::class.java))
    }

}