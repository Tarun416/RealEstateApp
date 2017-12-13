package com.talisman.app.ui.login.model

import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 11/17/17.
 */


data class LogInResponse(
        val error: String, //success
        val data: Data
) : BaseResponse()

data class Data(
        val User: User,
        val Business: Business,
        val vns: List<Vn>,
        val Locations: List<Location>
)

data class User(
        val id: String, //3199
        val name: String, //veena
        val mobile: String, //+918904577948
        val login_status: String, //ready
        val profile_pic: Any, //null
        val username: String, //veena123@gmail.com
        val group_id: String, //5
        val fcm_id: String //d9eLz2iz9bc:APA91bEoRD7EDWKvZSLkBvsQ9fJMeSKwDzVkToox92JakKy16IHaqSDC3-pERpRXHH9WxWYUHSLwkmH2L50Dp8DeIWyZysbTrgf-kSgdp1G5DiH2dIgyb2cs5XPFFZn26JbKFYso_1oz
)

data class Business(
        val crm_business_id: String, //71f5f2e7-a526-7f4b-a1be-5a0be099ae9f
        val reference_number: String, //b08354f3688c4e4e8c52c207d7d5b8c3
        val id: String //221
)

data class Vn(
        val BusinessVnMapping: BusinessVnMapping
)

data class BusinessVnMapping(
        val vn: String //+911133190778
)

data class Location(
        val id: String, //2
        val name: String, //Mumbai
        val api_url: String //http://mumbai.tecd.in:8200/pirest
)