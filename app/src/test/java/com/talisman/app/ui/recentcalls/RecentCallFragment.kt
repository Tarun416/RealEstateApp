package com.talisman.app.ui.recentcalls

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarun.talismanpi.R
import com.talisman.app.ui.recentcalldetails.RecentCallActivity
import kotlinx.android.synthetic.main.fragment_recent_call.*

/**
 * Created by varun on 11/9/17.
 */
class RecentCallFragment : Fragment(), RecentCallAdapter.ItemClickListener
{
    private lateinit var recentCallApapter : RecentCallAdapter

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
        return inflater?.inflate(R.layout.fragment_recent_call,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recentCallRecyclerView.layoutManager=LinearLayoutManager(activity)
        recentCallRecyclerView.setHasFixedSize(true)
        recentCallApapter=RecentCallAdapter(activity,this@RecentCallFragment)
        recentCallRecyclerView.adapter=recentCallApapter
    }

    override fun onCallClick() {
        startActivity(Intent(activity,RecentCallActivity::class.java))
    }

}