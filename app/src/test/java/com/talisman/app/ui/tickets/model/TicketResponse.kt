package com.talisman.app.ui.tickets.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Tarun on 11/24/17.
 */

@Entity(tableName = "TicketResponse")
data class TicketResponse( @PrimaryKey(autoGenerate = true)
						   var id : Int? = 0,
		var result_count: Int, //17
		var next_offset: Int, //17
		var entry_list: List<Entry>
		//var relationship_list: List<List<Any>>
)

data class Entry(
		var id: String, //25978fa8-3846-d19c-107e-5a1557899d51
		var module_name: String, //Cases
		@Embedded
		var name_value_list: NameValueList
)

data class NameValueList(
		@Embedded
		var assigned_user_name: AssignedUserName,
		@Embedded
		var modified_by_name: ModifiedByName,
		@Embedded
		var created_by_name: CreatedByName,
		@Embedded
		var id: Id,
		@Embedded
		var name: Name,
		@Embedded
		var date_entered: DateEntered,
		@Embedded
		var date_modified: DateModified,
		@Embedded
		var modified_user_id: ModifiedUserId,
		@Embedded
		var created_by: CreatedBy,
		@Embedded
		var description: Description,
		@Embedded
		var deleted: Deleted,
		@Embedded
		var assigned_user_id: AssignedUserId,
		@Embedded
		var case_number: CaseNumber,
		@Embedded
		var type: Type,
		@Embedded
		var status: Status,
		@Embedded
		var priority: Priority,
		@Embedded
		var resolution: Resolution,
		@Embedded
		var work_log: WorkLog,
		@Embedded
		var account_name: AccountName,
		@Embedded
		var account_id: AccountId,
		@Embedded
		var state: State,
		@Embedded
		var contact_created_by_name: ContactCreatedByName,
		@Embedded
		var contact_created_by_id: ContactCreatedById,
		@Embedded
		var jjwg_maps_lng_c: JjwgMapsLngC,
		@Embedded
		var jjwg_maps_lat_c: JjwgMapsLatC,
		@Embedded
		var jjwg_maps_geocode_status_c: JjwgMapsGeocodeStatusC,
		@Embedded
		var jjwg_maps_address_c: JjwgMapsAddressC
)

data class CaseNumber(
		var name: String, //case_number
		var value: String //69
)

data class ContactCreatedById(
		var name: String, //contact_created_by_id
		var value: String
)

data class DateModified(
		var name: String, //date_modified
		var value: String //2017-11-22 10:56:39
)

data class CreatedBy(
		var name: String, //created_by
		var value: String //1
)

data class Type(
		var name: String, //type
		var value: String //Administration
)

data class ModifiedByName(
		var name: String, //modified_by_name
		var value: String //Administrator
)

data class ModifiedUserId(
		var name: String, //modified_user_id
		var value: String //1
)

data class State(
		var name: String, //state
		var value: String //New
)

data class AssignedUserId(
		var name: String, //assigned_user_id
		var value: String //Manjula
)

data class Resolution(
		var name: String, //resolution
		var value: String
)

data class ContactCreatedByName(
		var name: String, //contact_created_by_name
		var value: String
)

data class AccountName(
		var name: String, //account_name
		var value: String
)

data class JjwgMapsGeocodeStatusC(
		var name: String, //jjwg_maps_geocode_status_c
		var value: String
)

data class CreatedByName(
		var name: String, //created_by_name
		var value: String //Administrator
)

data class Description(
		var name: String, //description
		var value: String //Ullas
)

data class Id(
		var name: String, //id
		var value: String //25978fa8-3846-d19c-107e-5a1557899d51
)

data class Priority(
		var name: String, //priority
		var value: String //High
)

data class JjwgMapsLatC(
		var name: String, //jjwg_maps_lat_c
		var value: String //0.00000000
)

data class Name(
		var name: String, //name
		var value: String //Testing
)

data class Status(
		var name: String, //status
		var value: String
)

data class Deleted(
		var name: String, //deleted
		var value: String //0
)

data class JjwgMapsLngC(
		var name: String, //jjwg_maps_lng_c
		var value: String //0.00000000
)

data class JjwgMapsAddressC(
		var name: String, //jjwg_maps_address_c
		var value: String
)

data class DateEntered(
		var name: String, //date_entered
		var value: String //2017-11-22 10:56:39
)

data class AssignedUserName(
		var name: String, //assigned_user_name
		var value: String
)

data class WorkLog(
		var name: String, //work_log
		var value: String //919886752077
)

data class AccountId(
		var name: String, //account_id
		var value: String
)