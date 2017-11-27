package com.talisman.app.ui.customers

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
import com.talisman.app.ui.customerdetails.CustomerDetailActivity
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
        initUi()

        customerPresenter.crmLogin()

    }

    private fun initUi() {
        customerList= ArrayList()
        setRecyclerView()
        clear_search.setOnClickListener(this)
    }

    private fun setRecyclerView() {
        customerRecyclerView.layoutManager = LinearLayoutManager(activity)
        customerRecyclerView.setHasFixedSize(true)
        customerAdapter = CustomerAdapter(activity, this@CustomerFragment,customerList)
        customerRecyclerView.adapter = customerAdapter
    }

    override fun onCustomerClick(position : Int) {
        customerPresenter.getCustomerDetails(customerList[position].name_value_list.phone_work.value)
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
        val intent = Intent(activity,RecentCallActivity::class.java)
        if(customerDetailsResponse!!.assigned_user_name!=null) {
            intent.putExtra("name", customerDetailsResponse!!.assigned_user_name.value)
            intent.putExtra("note", customerDetailsResponse.description.value)
        }

        startActivity(intent)

    }

    override fun showEmptyView() {
        searchContainer.visibility=View.GONE
        customerRecyclerView.visibility=View.GONE
        emptyText.visibility=View.VISIBLE
    }




}