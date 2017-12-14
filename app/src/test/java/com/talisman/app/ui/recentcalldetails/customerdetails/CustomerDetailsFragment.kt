package com.talisman.app.ui.recentcalldetails.customerdetails

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.ui.createcustomer.CreateCustomerActivity
import com.talisman.app.ui.recentcalldetails.customerdetails.model.CustomerDetailsResponse
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_call_customer_details.*
import java.util.*
import javax.inject.Inject
import android.app.AlarmManager
import android.app.Notification
import android.os.SystemClock
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.talisman.app.AlarmReceiver
import com.talisman.app.ui.home.HomeActivity
import com.talisman.app.ui.recentcalldetails.RecentCallActivity
import java.text.SimpleDateFormat


/**
 * Created by tarun on 11/10/17.
 */
class CustomerDetailsFragment : Fragment(), View.OnClickListener, CustomerDetailsContract.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Inject
    lateinit var presenter: CustomerDetailsPresenter

    private var phone: String = ""
    private lateinit var id: String
    private var callApiOnResume: Boolean = false
    private lateinit var customerDetailResponse: CustomerDetailsResponse
    private lateinit var date: Date
    private lateinit var calendar: Calendar

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

    private lateinit var simpleDateFormat: SimpleDateFormat

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerCustomerDetailsComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .customerDetailsModule(CustomerDetailsModule(this@CustomerDetailsFragment))
                .build().inject(this@CustomerDetailsFragment)

        callApiOnResume = false
        //  presenter.getCustomerDetails(this.phone.substring(3,this.phone.length))
        // date.setOnClickListener(this)
        // time.setOnClickListener(this)
        fab.setOnClickListener(this)

        phoneNumber.text = customerDetailResponse.phone_mobile!!.value
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        toggleViews(customerDetailResponse)
        scheduleButton.setOnClickListener(this)

    }

    private fun toggleViews(customerDetailResponse1: CustomerDetailsResponse) {

        firstName.text = customerDetailResponse1.first_name!!.value
        lastName.text = customerDetailResponse1.last_name!!.value

        when {
            !customerDetailResponse1.primary_address_street!!.value.isEmpty() -> {
                street.visibility = View.VISIBLE
                streetText.visibility = View.VISIBLE
                street.text = customerDetailResponse1.primary_address_street!!.value
            }
            else -> {
                street.visibility = View.GONE
                streetText.visibility = View.GONE
            }

        }

        when {
            !customerDetailResponse1.primary_address_city!!.value.isEmpty() -> {
                city.visibility = View.VISIBLE
                cityText.visibility = View.VISIBLE
                city.text = customerDetailResponse1.primary_address_city!!.value
            }
            else -> {
                city.visibility = View.GONE
                cityText.visibility = View.GONE
            }
        }

        when {
            !customerDetailResponse1.primary_address_state!!.value.isEmpty() -> {
                state.visibility = View.VISIBLE
                stateText.visibility = View.VISIBLE
                state.text = customerDetailResponse1.primary_address_state!!.value
            }
            else -> {
                state.visibility = View.GONE
                stateText.visibility = View.GONE
            }
        }

        when {
            !customerDetailResponse1.primary_address_country!!.value.isEmpty() -> {
                countryValue.visibility = View.VISIBLE
                countryText.visibility = View.VISIBLE
                countryValue.text = customerDetailResponse1.primary_address_country!!.value
            }
            else -> {
                countryValue.visibility = View.GONE
                countryText.visibility = View.GONE
            }
        }

        when {
            !customerDetailResponse1.primary_address_postalcode!!.value.isEmpty() -> {
                pincode.visibility = View.VISIBLE
                pinCodeText.visibility = View.VISIBLE
                pincode.text = customerDetailResponse1.primary_address_postalcode!!.value
            }
            else -> {
                pincode.visibility = View.GONE
                pinCodeText.visibility = View.GONE
            }
        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {

            R.id.fab -> {
                val intent = Intent(activity, CreateCustomerActivity::class.java)
                intent.putExtra("firstName", firstName.text.toString())
                intent.putExtra("lastName", lastName.text.toString())
                intent.putExtra("phone", "+"+phoneNumber.text.toString())
                intent.putExtra("city", city.text.toString())
                intent.putExtra("state", state.text.toString())
                intent.putExtra("country", countryValue.text.toString())
                intent.putExtra("pincode", pincode.text.toString())
                intent.putExtra("street", street.text.toString())
                intent.putExtra("id", id)

                callApiOnResume = true
                startActivity(intent)
            }

            R.id.scheduleButton -> {
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

        }

    }


    private fun invokeTimePickerDialog() {
        val now = Calendar.getInstance()
        val tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false)
        tpd.setMinTime(now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND))
        tpd.accentColor = ContextCompat.getColor(activity, R.color.green_color)
        tpd.show(activity.fragmentManager, "Timepickerdialog")
    }


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        invokeTimePickerDialog()
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.SECOND, second)
        date = calendar.time

        if(date.time < Date().time)
        {
            Toast.makeText(activity,"Invalid time",Toast.LENGTH_LONG).show()
            return
        }


        scheduleNotification(getNotification())
    }

    private fun scheduleNotification(notification: Notification) {
        val notificationIntent = Intent(activity, AlarmReceiver::class.java)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification)
        // notificationIntent.putExtra("customer_id", customerId)
        // notificationIntent.putExtra(, PhoneReceiver.customerName);
        val pendingIntent = PendingIntent.getBroadcast(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val futureInMillis = SystemClock.elapsedRealtime() + (calendar.time.time - Date().time)
      //  Log.d("compare time", (calendar.timeInMillis - System.currentTimeMillis()).toString() + " " + SystemClock.elapsedRealtime())
        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
        Toast.makeText(activity,"Your call has been scheduled at "+simpleDateFormat.format(calendar.time),Toast.LENGTH_LONG).show()
    }

    private fun getNotification(): Notification {
        val action1Intent = Intent(activity, RecentCallActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("customerDetailResponse",customerDetailResponse)
        action1Intent.putExtras(bundle)
        action1Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        //  action1Intent.putExtra("customer_id", customerId)
        val action1PendingIntent = PendingIntent.getActivity(activity, 0,
                action1Intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = android.support.v4.app.NotificationCompat.Builder(activity, "1")
        builder.setContentTitle("Scheduled call with " + customerDetailResponse.first_name!!.value)
        builder.setContentText("You have scheduled a call with " + customerDetailResponse.first_name!!.value)
        builder.setContentIntent(action1PendingIntent)
        builder.setStyle(NotificationCompat.BigTextStyle().bigText("You have scheduled a call with " + customerDetailResponse.first_name!!.value))
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(uri)
        builder.setSmallIcon(R.drawable.app_icon)
        return builder.build()
    }


    fun setPhone(phone: String) {
        this.phone = phone
    }

    override val isNetworkConnected: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress(message: String) {

    }

    override fun showProgress() {
        if (progressBar != null)
            progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        if (progressBar != null)
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


    override fun passCustomerDetails(t: CustomerDetailsResponse) {
        customerDetailResponse = t
        if (isResumed) {
            toggleViews(t)
        }

    }

    override fun showErrorMesssage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun setId(id: String?) {
        this.id = id!!
    }

    override fun onResume() {
        super.onResume()
        if (callApiOnResume)
            presenter.getCustomerDetails(this.phone.substring(1,phone.length))
    }

    fun setCustomerResponse(customerDetailResposne: CustomerDetailsResponse) {
        this.customerDetailResponse = customerDetailResposne
    }


}