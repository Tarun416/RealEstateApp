package com.talisman.app.ui.customers

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.talisman.app.ui.customers.model.CustomerResponse
import io.reactivex.Flowable

/**
 * Created by Tarun on 12/18/17.
 */
@Dao
interface CustomerDao {
    companion object {
        private const val QUERY_CUSTOMER_RESPONSE = "SELECT * FROM CustomerResponse "
        private const val DELETE_CUSTOMER_RESPONSE = "DELETE FROM CustomerResponse"
    }

    @Query(QUERY_CUSTOMER_RESPONSE)
    fun getCustomers(): Flowable<CustomerResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomers(customers: CustomerResponse)

    @Query(DELETE_CUSTOMER_RESPONSE)
    fun deleteCustomers()

}
