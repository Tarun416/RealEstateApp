package com.talisman.app.ui.tickets

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.ticketdetails.TicketDetailsActivity
import com.talisman.app.ui.tickets.model.Entry
import kotlinx.android.synthetic.main.fragment_tickets.*
import javax.inject.Inject

/**
 * Created by tarun on 11/9/17.
 */
class TicketFragment : Fragment(), TicketAdapter.OnTicketClick , View.OnClickListener, TicketContract.View {


    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var ticketList : ArrayList<Entry>

    @Inject
    lateinit var ticketPresenter : TicketPresenter

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

        DaggerTicketComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .ticketModule(TicketModule(this@TicketFragment))
                .build().inject(this@TicketFragment)
        initUi()
        ticketPresenter.crmLogin()
    }

    private fun initUi() {
        ticketList= ArrayList()
        setRecyclerView()
        filter.setOnClickListener(this)
        clear_search.setOnClickListener(this)


    }

    private fun setRecyclerView() {
        ticketRecyclerView.layoutManager = LinearLayoutManager(activity)
        ticketRecyclerView.setHasFixedSize(true)
        ticketAdapter = TicketAdapter(activity, this@TicketFragment,ticketList)
        ticketRecyclerView.adapter = ticketAdapter
    }

    override fun onTicketClick() {
        startActivity(Intent(activity, TicketDetailsActivity::class.java))
    }

    override fun onClick(p0: View?) {
      when(p0!!.id)
      {
          R.id.filter -> {}
          R.id.clear_search -> search.text.clear()
      }
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

    override fun crmLoginDone() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ticketPresenter.onStop()
    }

    override fun showTickets(entry_list: List<Entry>) {
        ticketList.clear()
        search.visibility=View.VISIBLE
        ticketRecyclerView.visibility=View.VISIBLE
        emptyText.visibility=View.GONE
        filter.visibility=View.VISIBLE
        ticketList.addAll(entry_list)
        ticketAdapter.notifyDataSetChanged()
    }

    override fun onError(message: String?) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
        searchContainer.visibility=View.GONE
        ticketRecyclerView.visibility=View.GONE
        emptyText.visibility=View.VISIBLE
        filter.visibility=View.GONE

    }

    override fun showEmptyView() {
        searchContainer.visibility=View.GONE
        ticketRecyclerView.visibility=View.GONE
        emptyText.visibility=View.VISIBLE
        filter.visibility=View.GONE
    }


}