package com.talisman.app.ui.tickets

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.customers.CustomerAdapter
import com.talisman.app.ui.tickets.model.Entry
import kotlinx.android.synthetic.main.ticket_items.view.*

/**
 * Created by tarun on 11/9/17.
 */
class TicketAdapter(private var context: Context,private var ticketClickListener: OnTicketClick, private var ticketList : ArrayList<Entry>) : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val entry = ticketList[position]

        holder!!.ticketNo.text = "Ticket No : "+entry.name_value_list.case_number.value
        holder.problem.text = entry.name_value_list.description.value
        holder.ticketContainer.setOnClickListener{
            ticketClickListener.onTicketClick(position)
        }

        when {
            entry.name_value_list.state.value.equals("New",true) -> holder.leftView.setBackgroundColor(ContextCompat.getColor(context,R.color.line_blue_color))
            entry.name_value_list.state.value.equals("Open",true) -> holder.leftView.setBackgroundColor(ContextCompat.getColor(context,R.color.line_red_color))
            entry.name_value_list.state.value.equals("Closed",true) -> holder.leftView.setBackgroundColor(ContextCompat.getColor(context,R.color.line_green_color))
            entry.name_value_list.state.value.equals("Clear",true) -> holder.leftView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary))

        }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.ticket_items, parent, false))
    }

    interface OnTicketClick
    {
        fun onTicketClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ticketNo = itemView.ticketNo!!
        val problem = itemView.problem!!
        val ticketContainer = itemView.ticketContainer!!
        val leftView = itemView.leftView!!
    }

    fun filterList(filteredItems: ArrayList<Entry>) {
        this.ticketList=filteredItems
        notifyDataSetChanged()
    }

}