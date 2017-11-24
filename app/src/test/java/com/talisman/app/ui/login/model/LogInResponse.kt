package com.talisman.app.ui.login.model

import android.arch.persistence.room.Entity
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
        val vns: List<Vns>,
        val Locations: List<Location>
)

data class Vns(
        val businessMapping: BusinessVnMapping
)

data class BusinessVnMapping(
        val vn: String
)

data class User(
        val id: String, //664
        val name: String, //Athmika
        val mobile: String, //+919611275535
        val login_status: String, //ready
        val profile_pic: Any, //null
        val username: String, //asp@gmail.com
        val group_id: String, //5
        val fcm_id: Any //null
)

data class Location(
        val id: String, //2
        val name: String, //Mumbai
        val api_url: String //http://mumbai.tecd.in:8200/pirest
)

data class Business(
        val crm_business_id: String, //null
        val reference_number: String, //ccb1d45fb76f7c5a0bf619f979c6cf36
        val id: String //165
)