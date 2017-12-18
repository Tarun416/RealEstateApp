package com.talisman.app.ui.recentcalls

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.kotlin.isOnline
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.createcustomer.CreateCustomerActivity
import com.talisman.app.ui.recentcalldetails.RecentCallActivity
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.ui.recentcalls.model.CDRJSON
import kotlinx.android.synthetic.main.fragment_recent_call.*
import javax.inject.Inject

/**
 * Created by tarun on 11/9/17.
 */
class RecentCallFragment : Fragment(), RecentCallAdapter.ItemClickListener, View.OnClickListener, RecentCallContract.View {


    private lateinit var recentCallApapter: RecentCallAdapter
    private lateinit var recentCallList: ArrayList<CDRJSON>


    @Inject
    lateinit var recentCallPresenter: RecentCallPresenter

    private lateinit var customerCreatePhone : String
    private lateinit var filteredItems: ArrayList<CDRJSON>

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): RecentCallFragment {
            val fragment = RecentCallFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        val TAG: String = RecentCallFragment.javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recent_call, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerRecentCallComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .recentCallModule(RecentCallModule(this))
                .build().inject(this)

        recentCallPresenter.getRecentCallsFromDb()
        initUi()

    }

    private fun initUi() {
        filteredItems = ArrayList()
        recentCallList = ArrayList()
        setRecyclerView()
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

        swipeRefresh.setOnRefreshListener { callRecentCallApi() }

        callRecentCallApi()

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

    }


    fun callRecentCallApi()
    {
        if (isOnline(activity))
            recentCallPresenter.getRecentCalls("")
        else {
            swipeRefresh.isRefreshing=false
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }


    private fun filter(toString: String) {

        filteredItems = ArrayList()

        if (recentCallList != null && recentCallList.size > 0) {
            recentCallList.filterTo(filteredItems) { it.cli.contains(toString,true) }

            if(recentCallApapter!=null)
                recentCallApapter.filterList(filteredItems)

        } else
            return
    }

    private fun setRecyclerView() {
        recentCallRecyclerView.layoutManager = LinearLayoutManager(activity)
        recentCallRecyclerView.setHasFixedSize(true)
        recentCallApapter = RecentCallAdapter(activity, this@RecentCallFragment, recentCallList,true)
        recentCallRecyclerView.adapter = recentCallApapter
    }

    override fun onCallClick(position: Int) {
        if(isOnline(activity)) {
            if (filteredItems != null && filteredItems.size > 0) {
                customerCreatePhone = filteredItems[position].cli.substring(0, filteredItems[position].cli.length)
                recentCallPresenter.getCustomerDetails(filteredItems[position].cli.substring(1, filteredItems[position].cli.length))
            } else {
                customerCreatePhone = recentCallList[position].cli.substring(0, recentCallList[position].cli.length)
                recentCallPresenter.getCustomerDetails(recentCallList[position].cli.substring(1, recentCallList[position].cli.length))
            }
        }
        else
        {
            Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
        }

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.clear_search -> search.text.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recentCallPresenter.onStop()
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        swipeRefresh.isRefreshing=false
        progressBar.visibility = View.GONE
    }

    override fun onUnknownError(message: String, error: String) {

    }

    override fun onTimeout() {

    }

    override fun onNetworkError() {

    }

    override fun onFailure(errorMsg: String) {

    }

    override fun onConnectionError() {

    }

    override fun showRecentCalls(list: ArrayList<CDRJSON>) {
        searchContainer.visibility = View.VISIBLE
        recentCallRecyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        recentCallList.clear()
        recentCallList.addAll(list)
        recentCallApapter.notifyDataSetChanged()
    }

    override fun resultError() {
        searchContainer.visibility = View.GONE
        recentCallRecyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    override fun passCustomerDetails(customerDetailsResponse: CustomerDetailsResponse?) {

        if (customerDetailsResponse!!.phone_mobile != null) {
            val intent = Intent(activity, RecentCallActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("customerDetailResponse",customerDetailsResponse)
            intent.putExtras(bundle)
            startActivity(intent)
        }

       else {
            val intent = Intent(activity, CreateCustomerActivity::class.java)
            intent.putExtra("phone",customerCreatePhone)
            startActivity(intent)
        }

    }

    override fun call(position: Int) {
        if(filteredItems!=null && filteredItems.size>0)
        {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + filteredItems[position].cli)//change the number
            startActivity(callIntent)
        }
        else
        {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + recentCallList[position].cli)//change the number
            startActivity(callIntent)
        }
    }


}