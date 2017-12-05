package com.talisman.app.ui.createcustomer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.tarun.talismanpi.R
import com.talisman.app.TalismanPiApplication
import com.talisman.app.utils.KeyboardUtils
import kotlinx.android.synthetic.main.toolbar_customer_details.*
import kotlinx.android.synthetic.main.activity_create_customer.*
import javax.inject.Inject

/**
 * Created by Tarun on 12/5/17.
 */
class CreateCustomerActivity : AppCompatActivity(), View.OnClickListener, CreateCustomerContract.View {

    @Inject
    lateinit var presenter: CreateCustomerPresenter

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_customer)
        setSupportActionBar(toolbar)

        DaggerCreateCustomerComponent.builder()
                .netComponent(TalismanPiApplication.mNetComponent)
                .createCustomerModule(CreateCustomerModule(this@CreateCustomerActivity))
                .build().inject(this@CreateCustomerActivity)

        initUi()
    }

    private fun initUi() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        done.text = "Save"
        cancel.visibility = View.GONE
        view.visibility = View.GONE
        if (intent.extras.containsKey("firstName")) {
            toolbarText.text = "Update Customer"
            id = intent.extras.getString("id")
            firstName.setText(intent.extras.getString("firstName"))
            lastName.setText(intent.extras.getString("lastName"))
            street.setText(intent.extras.getString("street"))
            city.setText(intent.extras.getString("city"))
            street.setText(intent.extras.getString("street"))
            countryValue.setText(intent.extras.getString("country"))
            state.setText(intent.extras.getString("state"))
            pincode.setText(intent.extras.getString("pincode"))

        } else
            toolbarText.text = "Create Customer"
        done.setOnClickListener(this)
        phoneNumber.text = intent.extras.getString("phone")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.done -> {

                KeyboardUtils.hideKeyboard(this@CreateCustomerActivity, firstName)
                if (firstName.text.isEmpty() || lastName.text.isEmpty() || phoneNumber.text.isEmpty()) {
                    Toast.makeText(this, "Name and number can't be empty", Toast.LENGTH_LONG).show()
                    return
                }

                if (phoneNumber.text.length != 10) {
                    Toast.makeText(this, "Phone number should be of 10 digit,Toast.LENGTH_LONG).show()", Toast.LENGTH_LONG).show()
                    return
                }

                presenter.crmLogin(firstName.text.toString(), lastName.text.toString(), phoneNumber.text.toString(),
                        street.text.toString(), city.text.toString(), state.text.toString(), countryValue.text.toString(),
                        pincode.text.toString(), id)

            }
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

    }

    override fun onTimeout() {

    }

    override fun onNetworkError() {

    }

    override fun onFailure(errorMsg: String) {

    }

    override fun onConnectionError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

    override fun onSuccess() {
        if (intent.extras.containsKey("firstName"))
            Toast.makeText(this, "Customer Updated", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Customer Created", Toast.LENGTH_LONG).show()

        finish()
    }


}
