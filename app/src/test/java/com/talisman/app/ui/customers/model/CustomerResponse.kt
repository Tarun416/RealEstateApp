package com.talisman.app.ui.customers.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 11/23/17.
 */

@Entity(tableName = "CustomerResponse")
data class CustomerResponse(@PrimaryKey(autoGenerate = true)
							var id : Int? = 0,
		var result_count: Int, //13
		var next_offset: Int, //13
		var entry_list: List<Entry>
		//var relationship_list: List<List<Any>>
)

data class Entry(
		var id: String, //3a330ca7-37b0-bce3-0edb-5a12ac6ca8ac
		var module_name: String, //Leads
		@Embedded
		var name_value_list: NameValueList
)

data class NameValueList(
		@Embedded
		var first_name: FirstName,
		@Embedded
		var last_name: LastName,
		@Embedded
		var phone_work: PhoneWork,
		@Embedded
		var primary_address_street: PrimaryAddressStreet,
		@Embedded
		var description: Description,
		@Embedded
		var id: Id,
		@Embedded
		var status: Status
)

data class Description(
		var name: String, //description
		var value: String //ullas
)

data class PhoneWork(
		var name: String, //phone_work
		var value: String //8951577980
)

data class Id(
		var name: String, //id
		var value: String //3a330ca7-37b0-bce3-0edb-5a12ac6ca8ac
)

data class LastName(
		var name: String, //last_name
		var value: String //ullastest
)

data class FirstName(
		var name: String, //first_name
		var value: String //ullastest
)

data class PrimaryAddressStreet(
		var name: String, //primary_address_street
		var value: String //no 13
)

data class Status(
		var name: String, //status
		var value: String //New
)