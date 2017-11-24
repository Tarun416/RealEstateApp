package com.talisman.app.model

/**
 * Created by Tarun on 11/23/17.
 */

data class CRMLoginResponse(
		val id: String, //ssglbckg54p0hjg0trbednovp0
		val module_name: String, //Users
		val name_value_list: NameValueList
)

data class NameValueList(
		val user_id: UserId,
		val user_name: UserName,
		val user_language: UserLanguage,
		val user_currency_id: UserCurrencyId,
		val user_currency_name: UserCurrencyName
)

data class UserName(
		val name: String, //user_name
		val value: String //admin
)

data class UserCurrencyId(
		val name: String, //user_currency_id
		val value: String
)

data class UserLanguage(
		val name: String, //user_language
		val value: String //en_us
)

data class UserCurrencyName(
		val name: String, //user_currency_name
		val value: String //US Dollars
)

data class UserId(
		val name: String, //user_id
		val value: String //1
)