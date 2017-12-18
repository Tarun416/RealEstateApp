package com.talisman.app.ui.customers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.talisman.app.ui.customers.model.Entry
import com.talisman.app.ui.recentcalldetails.RecentCallActivity
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import kotlinx.android.synthetic.main.fragment_customers.*
import javax.inject.Inject

/**
 * Created by tarun on 11/9/17.
 */
class CustomerFragment : Fragment(), CustomerAdapter.OnCustomerClick, View.OnClickListener, CustomerContract.View {

    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerList : ArrayList<Entry>

    @Inject
    lateinit var customerPresenter: CustomerPresenter

    private lateinit var filteredItems: ArrayList<Entry>

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): CustomerFragment {
            val fragment = CustomerFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        val TAG:String = CustomerFragment.javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_customers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerCustomerComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .customerModule(CustomerModule(this@CustomerFragment))
                .build().inject(this@CustomerFragment)
        customerPresenter.getCustomersFromDb()
        initUi()
        callgetCustomerApi()
    }

    fun callgetCustomerApi()
    {
        if(isOnline(activity))
        {
            customerPresenter.crmLogin()
        }
        else
        {
            swipeRefresh.isRefreshing=false
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun initUi() {
        customerList= ArrayList()
        filteredItems = ArrayList()
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

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        swipeRefresh.setOnRefreshListener { callgetCustomerApi() }
    }

    private fun filter(toString: String) {

        filteredItems = ArrayList()

        if (customerList != null && customerList.size > 0) {
            customerList.filterTo(filteredItems) { it.name_value_list.first_name.value.contains(toString,true) }

            if(customerAdapter!=null)
                customerAdapter.filterList(filteredItems)

        } else
            return
    }

    private fun setRecyclerView() {
        customerRecyclerView.layoutManager = LinearLayoutManager(activity)
        customerRecyclerView.setHasFixedSize(true)
        customerAdapter = CustomerAdapter(activity, this@CustomerFragment,customerList)
        customerRecyclerView.adapter = customerAdapter
    }

    override fun onCustomerClick(position : Int) {
        if(isOnline(activity)) {
            if (filteredItems != null && filteredItems.size > 0)
                customerPresenter.getCustomerDetails(filteredItems[position].name_value_list.phone_work.value)
            else
                customerPresenter.getCustomerDetails(customerList[position].name_value_list.phone_work.value)
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
        customerPresenter.onStop()
    }

    override fun showCustomers(entry_list: List<Entry>) {
        customerList.clear()
        searchContainer.visibility=View.VISIBLE
        customerRecyclerView.visibility=View.VISIBLE
        emptyText.visibility=View.GONE
        customerList.addAll(entry_list)
        customerAdapter.notifyDataSetChanged()
    }

    override fun onError(message: String?) {
      Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
      searchContainer.visibility=View.GONE
      customerRecyclerView.visibility=View.GONE
      emptyText.visibility=View.VISIBLE

    }

    override fun passCustomerDetails(customerDetailsResponse: CustomerDetailsResponse?) {
        if (customerDetailsResponse!!.phone_mobile != null) {
            val intent = Intent(activity, RecentCallActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("customerDetailResponse",customerDetailsResponse)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(activity,"Number not found",Toast.LENGTH_LONG).show()
        }


    }

    override fun showEmptyView() {
        searchContainer.visibility=View.GONE
        customerRecyclerView.visibility=View.GONE
        emptyText.visibility=View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        customerPresenter.crmLogin()
    }

    override fun call(position: Int) {
        if(filteredItems!=null && filteredItems.size>0)
        {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + filteredItems[position].name_value_list.phone_work.value)//change the number
            startActivity(callIntent)
        }
        else
        {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + customerList[position].name_value_list.phone_work.value)//change the number
            startActivity(callIntent)
        }
    }


}