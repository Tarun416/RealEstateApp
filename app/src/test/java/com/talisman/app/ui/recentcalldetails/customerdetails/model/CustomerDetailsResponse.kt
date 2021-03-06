package com.talisman.app.ui.recentcalldetails.customerdetails.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Tarun on 11/27/17.
 */

data class CustomerDetailsResponse(
		val assigned_user_name: AssignedUserName?,
		/*val modified_by_name: ModifiedByName,
		val created_by_name: CreatedByName,*/
		val id: Id?,
		/*val date_entered: DateEntered,
		val date_modified: DateModified,
		val modified_user_id: ModifiedUserId,
		val created_by: CreatedBy,*/
		val description: Description?,
		//val deleted: Deleted,
		val assigned_user_id: AssignedUserId?,
		//val salutation: Salutation,
		val first_name: FirstName?,
		val last_name: LastName?,
		val title: Title?,
		val photo: Photo?,
		//val department: Department,
		val do_not_call: DoNotCall?,
		val phone_home: PhoneHome?,
		val phone_mobile: PhoneMobile?,
		val phone_work: PhoneWork?,
		//	val phone_other: PhoneOther,
		//	val phone_fax: PhoneFax,
		//	val email1: Email1,
		//	val email2: Email2,
		val primary_address_street: PrimaryAddressStreet?,
		val primary_address_city: PrimaryAddressCity?,
		val primary_address_state: PrimaryAddressState?,
		val primary_address_postalcode: PrimaryAddressPostalcode?,
		val primary_address_country: PrimaryAddressCountry?
		/*val alt_address_street: AltAddressStreet,
		val alt_address_city: AltAddressCity,
		val alt_address_state: AltAddressState,
		val alt_address_postalcode: AltAddressPostalcode,
		val alt_address_country: AltAddressCountry,
		val assistant: Assistant,
		val assistant_phone: AssistantPhone,
		val converted: Converted,
		val refered_by: ReferedBy,
		val lead_source: LeadSource,
		val lead_source_description: LeadSourceDescription,
		val status: Status,
		val status_description: StatusDescription,
		val reports_to_id: ReportsToId,
		val report_to_name: ReportToName,
		val account_description: AccountDescription,
		val contact_id: ContactId,
		val account_id: AccountId,
		val opportunity_id: OpportunityId,
		val opportunity_name: OpportunityName,
		val opportunity_amount: OpportunityAmount,
		val campaign_id: CampaignId,
		val campaign_name: CampaignName,
		val c_accept_status_fields: CAcceptStatusFields,
		val m_accept_status_fields: MAcceptStatusFields,
		val birthdate: Birthdate,
		val portal_name: PortalName,
		val portal_app: PortalApp,
		val website: Website,
		val e_invite_status_fields: EInviteStatusFields,
		val e_accept_status_fields: EAcceptStatusFields,
		val jjwg_maps_lng_c: JjwgMapsLngC,
		val jjwg_maps_lat_c: JjwgMapsLatC,
		val jjwg_maps_geocode_status_c: JjwgMapsGeocodeStatusC,
		val jjwg_maps_address_c: JjwgMapsAddressC*/
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readParcelable<AssignedUserName>(AssignedUserName::class.java.classLoader),
			source.readParcelable<Id>(AssignedUserName::class.java.classLoader),
			source.readParcelable<Description>(Description::class.java.classLoader),
			source.readParcelable<AssignedUserId>(AssignedUserId::class.java.classLoader),
			source.readParcelable<FirstName>(FirstName::class.java.classLoader),
			source.readParcelable<LastName>(LastName::class.java.classLoader),
			source.readParcelable<Title>(Title::class.java.classLoader),
			source.readParcelable<Photo>(Photo::class.java.classLoader),
			source.readParcelable<DoNotCall>(DoNotCall::class.java.classLoader),
			source.readParcelable<PhoneHome>(PhoneHome::class.java.classLoader),
			source.readParcelable<PhoneMobile>(PhoneMobile::class.java.classLoader),
			source.readParcelable<PhoneWork>(PhoneWork::class.java.classLoader),
			source.readParcelable<PrimaryAddressStreet>(PrimaryAddressStreet::class.java.classLoader),
			source.readParcelable<PrimaryAddressCity>(PrimaryAddressCity::class.java.classLoader),
			source.readParcelable<PrimaryAddressState>(PrimaryAddressState::class.java.classLoader),
			source.readParcelable<PrimaryAddressPostalcode>(PrimaryAddressPostalcode::class.java.classLoader),
			source.readParcelable<PrimaryAddressCountry>(PrimaryAddressCountry::class.java.classLoader)
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeParcelable(assigned_user_name, 0)
		writeParcelable(id, 0)
		writeParcelable(description, 0)
		writeParcelable(assigned_user_id, 0)
		writeParcelable(first_name, 0)
		writeParcelable(last_name, 0)
		writeParcelable(title, 0)
		writeParcelable(photo, 0)
		writeParcelable(do_not_call, 0)
		writeParcelable(phone_home, 0)
		writeParcelable(phone_mobile, 0)
		writeParcelable(phone_work, 0)
		writeParcelable(primary_address_street, 0)
		writeParcelable(primary_address_city, 0)
		writeParcelable(primary_address_state, 0)
		writeParcelable(primary_address_postalcode, 0)
		writeParcelable(primary_address_country, 0)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<CustomerDetailsResponse> = object : Parcelable.Creator<CustomerDetailsResponse> {
			override fun createFromParcel(source: Parcel): CustomerDetailsResponse = CustomerDetailsResponse(source)
			override fun newArray(size: Int): Array<CustomerDetailsResponse?> = arrayOfNulls(size)
		}
	}
}

data class LeadSourceDescription(
		val name: String, //lead_source_description
		val value: String
)

data class OpportunityName(
		val name: String, //opportunity_name
		val value: String
)

data class DateModified(
		val name: String, //date_modified
		val value: String //2017-11-16 13:05:34
)

data class ContactId(
		val name: String, //contact_id
		val value: String
)

data class CreatedBy(
		val name: String, //created_by
		val value: String //1
)

data class PrimaryAddressPostalcode(
		val name: String, //primary_address_postalcode
		val value: String //560056
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PrimaryAddressPostalcode> = object : Parcelable.Creator<PrimaryAddressPostalcode> {
			override fun createFromParcel(source: Parcel): PrimaryAddressPostalcode = PrimaryAddressPostalcode(source)
			override fun newArray(size: Int): Array<PrimaryAddressPostalcode?> = arrayOfNulls(size)
		}
	}
}

data class PhoneOther(
		val name: String, //phone_other
		val value: String
)

data class OpportunityId(
		val name: String, //opportunity_id
		val value: String
)

data class Id(
		val name: String, //id
		val value: String //3f55b80b-406e-d7f8-791f-5a0d84076acb
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Id> = object : Parcelable.Creator<Id> {
			override fun createFromParcel(source: Parcel): Id = Id(source)
			override fun newArray(size: Int): Array<Id?> = arrayOfNulls(size)
		}
	}
}

data class AltAddressState(
		val name: String, //alt_address_state
		val value: String
)

data class CAcceptStatusFields(
		val name: String, //c_accept_status_fields
		val value: String
)

data class JjwgMapsGeocodeStatusC(
		val name: String, //jjwg_maps_geocode_status_c
		val value: String
)

data class AccountDescription(
		val name: String, //account_description
		val value: String
)

data class Photo(
		val name: String, //photo
		val value: String
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
			override fun createFromParcel(source: Parcel): Photo = Photo(source)
			override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
		}
	}
}

data class PhoneWork(
		val name: String, //phone_work
		val value: String //8951577970
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PhoneWork> = object : Parcelable.Creator<PhoneWork> {
			override fun createFromParcel(source: Parcel): PhoneWork = PhoneWork(source)
			override fun newArray(size: Int): Array<PhoneWork?> = arrayOfNulls(size)
		}
	}
}

data class PhoneFax(
		val name: String, //phone_fax
		val value: String
)

data class AccountId(
		val name: String, //account_id
		val value: String //71f5f2e7-a526-7f4b-a1be-5a0be099ae9f
)

data class Assistant(
		val name: String, //assistant
		val value: String
)

data class AltAddressCountry(
		val name: String, //alt_address_country
		val value: String
)

data class ReportToName(
		val name: String, //report_to_name
		val value: String
)

data class Email2(
		val name: String, //email2
		val value: String
)

data class Birthdate(
		val name: String, //birthdate
		val value: Boolean //false
)

data class StatusDescription(
		val name: String, //status_description
		val value: String
)

data class AltAddressPostalcode(
		val name: String, //alt_address_postalcode
		val value: String
)

data class Deleted(
		val name: String, //deleted
		val value: String //0
)

data class Description(
		val name: String, //description
		var value: String //tarun
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Description> = object : Parcelable.Creator<Description> {
			override fun createFromParcel(source: Parcel): Description = Description(source)
			override fun newArray(size: Int): Array<Description?> = arrayOfNulls(size)
		}
	}
}

data class Title(
		val name: String, //title
		val value: String //Mr
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Title> = object : Parcelable.Creator<Title> {
			override fun createFromParcel(source: Parcel): Title = Title(source)
			override fun newArray(size: Int): Array<Title?> = arrayOfNulls(size)
		}
	}
}

data class PrimaryAddressStreet(
		val name: String, //primary_address_street
		val value: String //bangalore
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PrimaryAddressStreet> = object : Parcelable.Creator<PrimaryAddressStreet> {
			override fun createFromParcel(source: Parcel): PrimaryAddressStreet = PrimaryAddressStreet(source)
			override fun newArray(size: Int): Array<PrimaryAddressStreet?> = arrayOfNulls(size)
		}
	}
}

data class LeadSource(
		val name: String, //lead_source
		val value: String //Campaign
)

data class OpportunityAmount(
		val name: String, //opportunity_amount
		val value: String
)

data class ReferedBy(
		val name: String, //refered_by
		val value: String
)

data class LastName(
		val name: String, //last_name
		val value: String //S
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<LastName> = object : Parcelable.Creator<LastName> {
			override fun createFromParcel(source: Parcel): LastName = LastName(source)
			override fun newArray(size: Int): Array<LastName?> = arrayOfNulls(size)
		}
	}
}

data class Salutation(
		val name: String, //salutation
		val value: String
)

data class AssignedUserName(
		val name: String, //assigned_user_name
		val value: String
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<AssignedUserName> = object : Parcelable.Creator<AssignedUserName> {
			override fun createFromParcel(source: Parcel): AssignedUserName = AssignedUserName(source)
			override fun newArray(size: Int): Array<AssignedUserName?> = arrayOfNulls(size)
		}
	}
}

data class Website(
		val name: String, //website
		val value: String
)

data class PortalName(
		val name: String, //portal_name
		val value: String
)

data class Converted(
		val name: String, //converted
		val value: String //0
)

data class ModifiedByName(
		val name: String, //modified_by_name
		val value: String //Administrator
)

data class FirstName(
		val name: String, //first_name
		val value: String //ullas
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<FirstName> = object : Parcelable.Creator<FirstName> {
			override fun createFromParcel(source: Parcel): FirstName = FirstName(source)
			override fun newArray(size: Int): Array<FirstName?> = arrayOfNulls(size)
		}
	}
}

data class ModifiedUserId(
		val name: String, //modified_user_id
		val value: String //1
)

data class Email1(
		val name: String, //email1
		val value: String
)

data class CampaignId(
		val name: String, //campaign_id
		val value: String
)

data class CreatedByName(
		val name: String, //created_by_name
		val value: String //Administrator
)

data class Department(
		val name: String, //department
		val value: String
)

data class PrimaryAddressCountry(
		val name: String, //primary_address_country
		val value: String //India
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PrimaryAddressCountry> = object : Parcelable.Creator<PrimaryAddressCountry> {
			override fun createFromParcel(source: Parcel): PrimaryAddressCountry = PrimaryAddressCountry(source)
			override fun newArray(size: Int): Array<PrimaryAddressCountry?> = arrayOfNulls(size)
		}
	}
}

data class AssignedUserId(
		val name: String, //assigned_user_id
		val value: String
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<AssignedUserId> = object : Parcelable.Creator<AssignedUserId> {
			override fun createFromParcel(source: Parcel): AssignedUserId = AssignedUserId(source)
			override fun newArray(size: Int): Array<AssignedUserId?> = arrayOfNulls(size)
		}
	}
}

data class MAcceptStatusFields(
		val name: String, //m_accept_status_fields
		val value: String
)

data class JjwgMapsLatC(
		val name: String, //jjwg_maps_lat_c
		val value: String //0.00000000
)

data class Status(
		val name: String, //status
		val value: String //Assigned
)

data class PhoneHome(
		val name: String, //phone_home
		val value: String
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PhoneHome> = object : Parcelable.Creator<PhoneHome> {
			override fun createFromParcel(source: Parcel): PhoneHome = PhoneHome(source)
			override fun newArray(size: Int): Array<PhoneHome?> = arrayOfNulls(size)
		}
	}
}

data class PrimaryAddressCity(
		val name: String, //primary_address_city
		val value: String //bangalore
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PrimaryAddressCity> = object : Parcelable.Creator<PrimaryAddressCity> {
			override fun createFromParcel(source: Parcel): PrimaryAddressCity = PrimaryAddressCity(source)
			override fun newArray(size: Int): Array<PrimaryAddressCity?> = arrayOfNulls(size)
		}
	}
}

data class DateEntered(
		val name: String, //date_entered
		val value: String //2017-11-16 12:27:50
)

data class AltAddressStreet(
		val name: String, //alt_address_street
		val value: String
)

data class JjwgMapsLngC(
		val name: String, //jjwg_maps_lng_c
		val value: String //0.00000000
)

data class PrimaryAddressState(
		val name: String, //primary_address_state
		val value: String //bangalore
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PrimaryAddressState> = object : Parcelable.Creator<PrimaryAddressState> {
			override fun createFromParcel(source: Parcel): PrimaryAddressState = PrimaryAddressState(source)
			override fun newArray(size: Int): Array<PrimaryAddressState?> = arrayOfNulls(size)
		}
	}
}

data class AssistantPhone(
		val name: String, //assistant_phone
		val value: String
)

data class DoNotCall(
		val name: String, //do_not_call
		val value: String //0
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<DoNotCall> = object : Parcelable.Creator<DoNotCall> {
			override fun createFromParcel(source: Parcel): DoNotCall = DoNotCall(source)
			override fun newArray(size: Int): Array<DoNotCall?> = arrayOfNulls(size)
		}
	}
}

data class PortalApp(
		val name: String, //portal_app
		val value: String
)

data class JjwgMapsAddressC(
		val name: String, //jjwg_maps_address_c
		val value: String
)

data class ReportsToId(
		val name: String, //reports_to_id
		val value: String
)

data class AltAddressCity(
		val name: String, //alt_address_city
		val value: String
)

data class PhoneMobile(
		val name: String, //phone_mobile
		val value: String //8951577970
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeString(value)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<PhoneMobile> = object : Parcelable.Creator<PhoneMobile> {
			override fun createFromParcel(source: Parcel): PhoneMobile = PhoneMobile(source)
			override fun newArray(size: Int): Array<PhoneMobile?> = arrayOfNulls(size)
		}
	}
}

data class CampaignName(
		val name: String, //campaign_name
		val value: String
)

data class EInviteStatusFields(
		val name: String, //e_invite_status_fields
		val value: String
)

data class EAcceptStatusFields(
		val name: String, //e_accept_status_fields
		val value: String
)



