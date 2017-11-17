package com.talisman.app.ui.recentcalls

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.recent_call_items.view.*

/**
 * Created by tarun on 11/9/17.
 */
class RecentCallAdapter(private var context: Context, private var callClickListener : ItemClickListener) : RecyclerView.Adapter<RecentCallAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.name.text="Ananya Chauhan"
        holder!!.date.text="11:37 PM"
        holder!!.icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_call_missed))

        holder.recentCallContainer.setOnClickListener {
            callClickListener.onCallClick()
        }
    }

    override fun getItemCount(): Int {
         return 10
    }

    interface ItemClickListener
    {
        fun onCallClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_call_items,parent,false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name = itemView.nameOrNumber
        val date = itemView.date
        val icon = itemView.callStatusIcon
        val recentCallContainer = itemView.recentCallContainer

    }

}