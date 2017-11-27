package com.talisman.app.ui.recentcalldetails.customerdetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.talismanpi.R
import kotlinx.android.synthetic.main.fragment_call_customer_details.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*


/**
 * Created by tarun on 11/10/17.
 */
class CustomerDetailsFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var name : String

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(): CustomerDetailsFragment {
            val fragment = CustomerDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_call_customer_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date.setOnClickListener(this)
        time.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.date -> {
                val now = Calendar.getInstance()
                val dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                )
                dpd.minDate = now
                dpd.accentColor = ContextCompat.getColor(activity, R.color.green_color)
                dpd.show(activity.fragmentManager, "Datepickerdialog")
            }


            R.id.time -> {
                when {
                    date.text.toString().equals("set date",true) -> {
                        Toast.makeText(activity,"Please select date first",Toast.LENGTH_LONG).show()
                        return
                    }
                    else -> {
                        val now = Calendar.getInstance()
                        val tpd = TimePickerDialog.newInstance(
                                this,
                                now.get(Calendar.HOUR),
                                now.get(Calendar.MINUTE),
                                false)
                        tpd.setMinTime(now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND))
                        tpd.accentColor = ContextCompat.getColor(activity, R.color.green_color)
                        tpd.show(activity.fragmentManager, "Timepickerdialog")

                    }
                }

            }

        }

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        date.text = dayOfMonth.toString() + "/" + monthOfYear.toString() + "/" + year.toString()
        when {
            time.text.toString().equals("set time",true) -> time.callOnClick()
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        time.text = hourOfDay.toString() + ":" + minute.toString() + ":" + second.toString()
    }

    fun setDetails(name: String) {
         this.name=name
    }


}