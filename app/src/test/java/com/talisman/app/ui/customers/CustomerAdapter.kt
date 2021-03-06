package com.talisman.app.ui.customers

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.customers.model.Entry
import kotlinx.android.synthetic.main.customer_items.view.*
import java.util.*

/**
 * Created by tarun on 11/9/17.
 */
class CustomerAdapter(private var context: Context, private var customerClickListener: OnCustomerClick, private var customerList: ArrayList<Entry>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private var sampleDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.customer_circle)!!

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.initialText.text = customerList[position].name_value_list.first_name.value.substring(0, 1).toUpperCase()
        holder!!.name.text = customerList[position].name_value_list.first_name.value

        holder.customerContainer.setOnClickListener {
            customerClickListener.onCustomerClick(position)
        }

        holder.call.setOnClickListener({
            customerClickListener.call(position)
        })

        sampleDrawable = context.resources.getDrawable(R.drawable.customer_circle)
        sampleDrawable.colorFilter = PorterDuffColorFilter(customerList[position].randomColour, PorterDuff.Mode.MULTIPLY)
        holder.initialText.background = sampleDrawable
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_items, parent, false));
    }

    interface OnCustomerClick {
        fun onCustomerClick(position: Int)
        fun call(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val initialText = itemView.customerInitialText!!
        val name = itemView.name!!
        val customerContainer = itemView.customerContainer!!
        val call = itemView.call
    }

    fun filterList(filteredItems: ArrayList<Entry>) {
        this.customerList = filteredItems
        notifyDataSetChanged()
    }

}