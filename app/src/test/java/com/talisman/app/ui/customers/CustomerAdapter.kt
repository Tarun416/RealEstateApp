package com.talisman.app.ui.customers

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.customer_items.view.*

/**
 * Created by tarun on 11/9/17.
 */
class CustomerAdapter(private var context: Context, private var customerClickListener: OnCustomerClick) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.initialText.text = "S"
        holder!!.name.text = "Ananya"

        holder.customerContainer.setOnClickListener {
            customerClickListener.onCustomerClick()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_items, parent, false));
    }

    interface OnCustomerClick {
        fun onCustomerClick()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val initialText = itemView.customerInitialText!!
        val name = itemView.name!!
        val customerContainer = itemView.customerContainer!!
    }

}