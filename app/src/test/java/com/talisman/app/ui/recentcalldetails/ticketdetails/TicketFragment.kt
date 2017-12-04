package com.talisman.app.ui.recentcalldetails.ticketdetails

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.ticketdetails.TicketDetailsActivity
import com.talisman.app.ui.tickets.TicketAdapter
import com.talisman.app.ui.tickets.model.Entry
import kotlinx.android.synthetic.main.fragment_recentcall_tickets.*
import javax.inject.Inject

/**
 * Created by tarun on 11/10/17.
 */
class TicketFragment : Fragment() , TicketContract.View , View.OnClickListener , TicketAdapter.OnTicketClick
{

    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var ticketList: ArrayList<Entry>

    private lateinit var filteredItems: ArrayList<Entry>

    private lateinit var tempItem: ArrayList<Entry>

    private var backPressApiCall : Boolean = false

    @Inject
    lateinit var ticketPresenter: TicketPresenter

    private  var phone: String =""

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
        return inflater?.inflate(R.layout.fragment_recentcall_tickets,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerTicketRDetailsComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .ticketModule(TicketModule(this@TicketFragment))
                .build().inject(this@TicketFragment)


        backPressApiCall=false
        initUi()
        filteredItems= ArrayList()
        ticketPresenter.crmLogin(phone)
        tempItem = ArrayList()
    }

    private fun initUi() {
        ticketList = ArrayList()
        setRecyclerView()
        filter.setOnClickListener(this)
        clear_search.setOnClickListener(this)

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })
    }

    private fun filter(toString: String) {

        filteredItems = ArrayList()

        if (tempItem != null && tempItem.size > 0) {
            tempItem.filterTo(filteredItems) { it.name_value_list.description.value.contains(toString, true) }

            if (ticketAdapter != null)
                ticketAdapter.filterList(filteredItems)
        } else if (ticketList != null && ticketList.size > 0) {
            ticketList.filterTo(filteredItems) { it.name_value_list.description.value.contains(toString, true) }

            if (ticketAdapter != null)
                ticketAdapter.filterList(filteredItems)

        } else
            return
    }


    private fun setRecyclerView() {
        ticketRecyclerView.layoutManager = LinearLayoutManager(activity)
        ticketRecyclerView.setHasFixedSize(true)
        ticketAdapter = TicketAdapter(activity, this@TicketFragment, ticketList)
        ticketRecyclerView.adapter = ticketAdapter
    }

    override fun onTicketClick(position : Int) {
        val intent = Intent(activity, TicketDetailsActivity::class.java)

        if(filteredItems!=null && filteredItems.size>0)
        {
            intent.putExtra("ticketNumber",filteredItems[position].name_value_list.case_number.value)
            intent.putExtra("status", filteredItems[position].name_value_list.state.value)
            intent.putExtra("priority", filteredItems[position].name_value_list.priority.value)
            intent.putExtra("description", filteredItems[position].name_value_list.description.value)
            intent.putExtra("resolution", filteredItems[position].name_value_list.resolution.value)
            intent.putExtra("workLog", filteredItems[position].name_value_list.work_log.value)
            intent.putExtra("ticketId", filteredItems[position].id)
        }
        else {
            intent.putExtra("ticketNumber", ticketList[position].name_value_list.case_number.value)
            intent.putExtra("status", ticketList[position].name_value_list.state.value)
            intent.putExtra("priority", ticketList[position].name_value_list.priority.value)
            intent.putExtra("description", ticketList[position].name_value_list.description.value)
            intent.putExtra("resolution", ticketList[position].name_value_list.resolution.value)
            intent.putExtra("workLog", ticketList[position].name_value_list.work_log.value)
            intent.putExtra("ticketId", ticketList[position].id)
        }

        backPressApiCall=true
        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.filter -> {
                showDialog()
            }
            R.id.clear_search -> search.text.clear()
        }
    }

    private fun showDialog() {
        filteredItems = ArrayList()

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val newRadioButton = dialog.findViewById<RadioButton>(R.id.newText)
        val allRadioButton = dialog.findViewById<RadioButton>(R.id.all)
        val closeRadioButton = dialog.findViewById<RadioButton>(R.id.close)
        val clearRadioButton = dialog.findViewById<RadioButton>(R.id.clear)
        val openRadioButton = dialog.findViewById<RadioButton>(R.id.open)


        newRadioButton.setOnClickListener {
            newRadioButton.isChecked = true
            ticketList.filterTo(filteredItems) { it.name_value_list.state.value.contains("new", true) }

            if (ticketAdapter != null) {
                ticketAdapter.filterList(filteredItems)
                tempItem.clear()
                tempItem.addAll(filteredItems)
            }

            dialog.dismiss()
        }

        closeRadioButton.setOnClickListener {
            newRadioButton.isChecked = true
            ticketList.filterTo(filteredItems) { it.name_value_list.state.value.contains("closed", true) }

            if (ticketAdapter != null) {
                ticketAdapter.filterList(filteredItems)
                tempItem.clear()
                tempItem.addAll(filteredItems)
            }

            dialog.dismiss()
        }

        clearRadioButton.setOnClickListener {
            newRadioButton.isChecked = true
            ticketList.filterTo(filteredItems) { it.name_value_list.state.value.contains("clear", true) }

            if (ticketAdapter != null) {
                ticketAdapter.filterList(filteredItems)
                tempItem.clear()
                tempItem.addAll(filteredItems)
            }

            dialog.dismiss()
        }

        openRadioButton.setOnClickListener {
            newRadioButton.isChecked = true
            ticketList.filterTo(filteredItems) { it.name_value_list.state.value.contains("open", true) }

            if (ticketAdapter != null) {
                ticketAdapter.filterList(filteredItems)
                tempItem.clear()
                tempItem.addAll(filteredItems)
            }

            dialog.dismiss()
        }


        allRadioButton.setOnClickListener {
            allRadioButton.isChecked = true
            ticketList.filterTo(filteredItems) { it.name_value_list.state.value.contains("", true) }

            if (ticketAdapter != null) {
                ticketAdapter.filterList(filteredItems)
                tempItem.clear()

            }

            dialog.dismiss()
        }

        dialog.show()
    }


    fun setPhone(phone: String?) {
        this.phone = phone!!
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onUnknownError(message: String, error: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onTimeout() {

    }

    override fun onNetworkError() {

    }

    override fun onFailure(errorMsg: String) {

    }

    override fun onConnectionError() {

    }


    override fun onDestroy() {
        super.onDestroy()
        ticketPresenter.onStop()
    }

    override fun showTickets(entry_list: List<Entry>) {
        ticketList.clear()
        search.visibility = View.VISIBLE
        ticketRecyclerView.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
        filter.visibility = View.VISIBLE
        if(filteredItems!=null && filteredItems.size>0) {
            filteredItems.clear()
            filteredItems.addAll(entry_list)
            ticketList.addAll(entry_list)
        }
        else
            ticketList.addAll(entry_list)
        ticketAdapter.notifyDataSetChanged()
    }

    override fun onError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        searchContainer.visibility = View.GONE
        ticketRecyclerView.visibility = View.GONE
        emptyText.visibility = View.VISIBLE
        filter.visibility = View.GONE

    }

    override fun showEmptyView() {
        searchContainer.visibility = View.GONE
        ticketRecyclerView.visibility = View.GONE
        emptyText.visibility = View.VISIBLE
        filter.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        if(backPressApiCall)
            ticketPresenter.crmLogin(phone)
    }


}