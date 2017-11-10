package com.talisman.app.ui.tickets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.customers.CustomerAdapter
import kotlinx.android.synthetic.main.ticket_items.view.*

/**
 * Created by varun on 11/9/17.
 */
class TicketAdapter(private var context: Context,private var ticketClickListener: OnTicketClick) : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.ticketNo.text = "Ticket No : 223"
        holder.problem.text = "Hardware problem"
        holder.ticketContainer.setOnClickListener{
            ticketClickListener.onTicketClick()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.ticket_items, parent, false))
    }

    interface OnTicketClick
    {
        fun onTicketClick()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ticketNo = itemView.ticketNo!!
        val problem = itemView.problem!!
        val ticketContainer = itemView.ticketContainer!!
    }

}