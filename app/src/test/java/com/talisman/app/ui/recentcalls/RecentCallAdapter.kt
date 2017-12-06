package com.talisman.app.ui.recentcalls

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.recentcalls.model.CDRJSON
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import kotlinx.android.synthetic.main.recent_call_items.view.*
import java.text.SimpleDateFormat

/**
 * Created by tarun on 11/9/17.
 */
class RecentCallAdapter(private var context: Context, private var callClickListener: ItemClickListener, private var recentCallList: ArrayList<CDRJSON>) : RecyclerView.Adapter<RecentCallAdapter.ViewHolder>() {
    private val simpledateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var df1 = SimpleDateFormat("d/MMM/yyyy HH:mm:ss")

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val cdrJson = recentCallList[position]

        holder!!.name.text = cdrJson.cli
        holder!!.date.text = df1.format(simpledateFormat.parse(cdrJson.startTime))

        if (cdrJson.callType == "I" && cdrJson.patched == "0")
            holder!!.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_call_missed))
        else if (cdrJson.callType == "I" && cdrJson.patched == "1")
            holder!!.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_call_received))
        else if (cdrJson.callType == "0" && cdrJson.patched == "0")
            holder!!.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_call_outgoing_missed))
        else
            holder!!.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_call_outgoing_received))


        holder.recentCallContainer.setOnClickListener {
            callClickListener.onCallClick(position)
        }

        holder.call.setOnClickListener({
            callClickListener.call(position)
        })

    }

    override fun getItemCount(): Int {
        return recentCallList.size
    }

    interface ItemClickListener {
        fun onCallClick(position: Int)
        fun call(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_call_items, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.nameOrNumber
        val date = itemView.date
        val icon = itemView.callStatusIcon
        val recentCallContainer = itemView.recentCallContainer
        val call = itemView.call

    }

    fun filterList(filteredItems: ArrayList<CDRJSON>) {
        this.recentCallList=filteredItems
        notifyDataSetChanged()
    }

}