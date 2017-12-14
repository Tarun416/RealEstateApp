package com.talisman.app.ui.recentcalldetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.talisman.app.ui.recentcalls.*
import com.talisman.app.ui.recentcalls.model.CDRJSON
import kotlinx.android.synthetic.main.fragment_recent_call.*
import javax.inject.Inject

/**
 * Created by tarun on 11/10/17.
 */
class CallHistoryFragment : Fragment() , RecentCallAdapter.ItemClickListener, RecentCallContract.View
{

    @Inject
    lateinit var recentCallPresenter: RecentCallPresenter

    private lateinit var phone: String
    private lateinit var recentCallList: ArrayList<CDRJSON>
    private lateinit var recentCallApapter: RecentCallAdapter

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): CallHistoryFragment {
            val fragment = CallHistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recent_call,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerRecentCallComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .recentCallModule(RecentCallModule(this))
                .build().inject(this)

        recentCallList=ArrayList()
        searchContainer.visibility=View.GONE
        swipeRefresh.isEnabled=false
        setRecyclerView()
        recentCallPresenter.getRecentCalls(phone)
    }


    private fun setRecyclerView() {
            recentCallRecyclerView.layoutManager = LinearLayoutManager(activity)
            recentCallRecyclerView.setHasFixedSize(true)
            recentCallApapter = RecentCallAdapter(activity, this@CallHistoryFragment, recentCallList , false)
            recentCallRecyclerView.adapter = recentCallApapter
        }


    override fun onCallClick(position: Int) {

    }

    override fun call(position: Int) {

    }


    fun setPhone(phone: String) {
        this.phone = phone
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
        recentCallRecyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        recentCallList.clear()
        recentCallList.addAll(list)
        recentCallApapter.notifyDataSetChanged()
    }

    override fun resultError() {
        recentCallRecyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    override fun passCustomerDetails(customerDetailsResponse: CustomerDetailsResponse?) {

    }



}