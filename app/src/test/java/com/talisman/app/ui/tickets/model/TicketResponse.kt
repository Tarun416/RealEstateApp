package com.talisman.app.ui.tickets.model

/**
 * Created by Tarun on 11/24/17.
 */

data class TicketResponse(
		val result_count: Int, //17
		val next_offset: Int, //17
		val entry_list: List<Entry>,
		val relationship_list: List<List<Any>>
)

data class Entry(
		val id: String, //25978fa8-3846-d19c-107e-5a1557899d51
		val module_name: String, //Cases
		val name_value_list: NameValueList
)

data class NameValueList(
		val assigned_user_name: AssignedUserName,
		val modified_by_name: ModifiedByName,
		val created_by_name: CreatedByName,
		val id: Id,
		val name: Name,
		val date_entered: DateEntered,
		val date_modified: DateModified,
		val modified_user_id: ModifiedUserId,
		val created_by: CreatedBy,
		val description: Description,
		val deleted: Deleted,
		val assigned_user_id: AssignedUserId,
		val case_number: CaseNumber,
		val type: Type,
		val status: Status,
		val priority: Priority,
		val resolution: Resolution,
		val work_log: WorkLog,
		val account_name: AccountName,
		val account_id: AccountId,
		val state: State,
		val contact_created_by_name: ContactCreatedByName,
		val contact_created_by_id: ContactCreatedById,
		val jjwg_maps_lng_c: JjwgMapsLngC,
		val jjwg_maps_lat_c: JjwgMapsLatC,
		val jjwg_maps_geocode_status_c: JjwgMapsGeocodeStatusC,
		val jjwg_maps_address_c: JjwgMapsAddressC
)

data class CaseNumber(
		val name: String, //case_number
		val value: String //69
)

data class ContactCreatedById(
		val name: String, //contact_created_by_id
		val value: String
)

data class DateModified(
		val name: String, //date_modified
		val value: String //2017-11-22 10:56:39
)

data class CreatedBy(
		val name: String, //created_by
		val value: String //1
)

data class Type(
		val name: String, //type
		val value: String //Administration
)

data class ModifiedByName(
		val name: String, //modified_by_name
		val value: String //Administrator
)

data class ModifiedUserId(
		val name: String, //modified_user_id
		val value: String //1
)

data class State(
		val name: String, //state
		val value: String //New
)

data class AssignedUserId(
		val name: String, //assigned_user_id
		val value: String //Manjula
)

data class Resolution(
		val name: String, //resolution
		val value: String
)

data class ContactCreatedByName(
		val name: String, //contact_created_by_name
		val value: String
)

data class AccountName(
		val name: String, //account_name
		val value: String
)

data class JjwgMapsGeocodeStatusC(
		val name: String, //jjwg_maps_geocode_status_c
		val value: String
)

data class CreatedByName(
		val name: String, //created_by_name
		val value: String //Administrator
)

data class Description(
		val name: String, //description
		val value: String //Ullas
)

data class Id(
		val name: String, //id
		val value: String //25978fa8-3846-d19c-107e-5a1557899d51
)

data class Priority(
		val name: String, //priority
		val value: String //High
)

data class JjwgMapsLatC(
		val name: String, //jjwg_maps_lat_c
		val value: String //0.00000000
)

data class Name(
		val name: String, //name
		val value: String //Testing
)

data class Status(
		val name: String, //status
		val value: String
)

data class Deleted(
		val name: String, //deleted
		val value: String //0
)

data class JjwgMapsLngC(
		val name: String, //jjwg_maps_lng_c
		val value: String //0.00000000
)

data class JjwgMapsAddressC(
		val name: String, //jjwg_maps_address_c
		val value: String
)

data class DateEntered(
		val name: String, //date_entered
		val value: String //2017-11-22 10:56:39
)

data class AssignedUserName(
		val name: String, //assigned_user_name
		val value: String
)

data class WorkLog(
		val name: String, //work_log
		val value: String //919886752077
)

data class AccountId(
		val name: String, //account_id
		val value: String
)