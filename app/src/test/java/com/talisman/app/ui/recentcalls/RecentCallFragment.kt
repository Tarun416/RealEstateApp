package com.talisman.app.ui.recentcalls

import android.content.Intent
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
        initUi()

    }

    private fun initUi() {
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

        if (isOnline(activity))
            recentCallPresenter.getRecentCalls()
        else
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
    }

    private fun filter(toString: String) {
        val filteredItems = ArrayList<CDRJSON>()

        if (recentCallList != null && recentCallList.size > 0) {
            recentCallList.filterTo(filteredItems) { it.cli.contains("+91"+toString,true) }

            if(recentCallApapter!=null)
                recentCallApapter.filterList(filteredItems)

        } else
            return
    }

    private fun setRecyclerView() {
        recentCallRecyclerView.layoutManager = LinearLayoutManager(activity)
        recentCallRecyclerView.setHasFixedSize(true)
        recentCallApapter = RecentCallAdapter(activity, this@RecentCallFragment, recentCallList)
        recentCallRecyclerView.adapter = recentCallApapter
    }

    override fun onCallClick(position: Int) {
       // recentCallPresenter.getCustomerDetails("8951577970")
        // startActivity(Intent(activity, RecentCallActivity::class.java))

        val intent = Intent(activity, RecentCallActivity::class.java)
        intent.putExtra("phoneNumber",recentCallList[position].cli)
        startActivity(intent)
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
        val intent = Intent(activity, RecentCallActivity::class.java)
        if (customerDetailsResponse!!.assigned_user_name != null) {
            intent.putExtra("firstName", customerDetailsResponse!!.first_name.value)
            intent.putExtra("lastName",customerDetailsResponse!!.last_name.value)
           // intent.putExtra("phoneNumber",cus)
            intent.putExtra("note", customerDetailsResponse.description.value)
        }

        startActivity(intent)

    }


}