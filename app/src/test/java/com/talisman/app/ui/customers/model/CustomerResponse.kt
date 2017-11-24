package com.talisman.app.ui.customers.model

import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 11/23/17.
 */

data class CustomerResponse(
		val result_count: Int, //13
		val next_offset: Int, //13
		val entry_list: List<Entry>,
		val relationship_list: List<List<Any>>
)

data class Entry(
		val id: String, //3a330ca7-37b0-bce3-0edb-5a12ac6ca8ac
		val module_name: String, //Leads
		val name_value_list: NameValueList
)

data class NameValueList(
		val first_name: FirstName,
		val last_name: LastName,
		val phone_work: PhoneWork,
		val primary_address_street: PrimaryAddressStreet,
		val description: Description,
		val id: Id,
		val status: Status
)

data class Description(
		val name: String, //description
		val value: String //ullas
)

data class PhoneWork(
		val name: String, //phone_work
		val value: String //8951577980
)

data class Id(
		val name: String, //id
		val value: String //3a330ca7-37b0-bce3-0edb-5a12ac6ca8ac
)

data class LastName(
		val name: String, //last_name
		val value: String //ullastest
)

data class FirstName(
		val name: String, //first_name
		val value: String //ullastest
)

data class PrimaryAddressStreet(
		val name: String, //primary_address_street
		val value: String //no 13
)

data class Status(
		val name: String, //status
		val value: String //New
)