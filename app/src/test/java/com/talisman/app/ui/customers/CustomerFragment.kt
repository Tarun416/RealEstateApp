package com.talisman.app.ui.customers

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.customerdetails.CustomerDetailActivity
import kotlinx.android.synthetic.main.fragment_customers.*

/**
 * Created by varun on 11/9/17.
 */
class CustomerFragment : Fragment(), CustomerAdapter.OnCustomerClick
{
    private lateinit var customerAdapter: CustomerAdapter

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_customers,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        customerRecyclerView.layoutManager= LinearLayoutManager(activity)
        customerRecyclerView.setHasFixedSize(true)
        customerAdapter=CustomerAdapter(activity,this@CustomerFragment)
        customerRecyclerView.adapter=customerAdapter
    }

    override fun onCustomerClick() {
        startActivity(Intent(activity,CustomerDetailActivity::class.java))
    }


}