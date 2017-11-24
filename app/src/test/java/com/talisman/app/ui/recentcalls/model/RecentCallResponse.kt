package com.talisman.app.ui.recentcalls.model

import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 11/21/17.
 */

data class RecentCallResponse (
		val CDRJSON: ArrayList<CDRJSON>
): BaseResponse()

data class CDRJSON(
		val attempt: Int, //1
		val endtype: String, //CALL-DISCONNECT
		val callNo: String, //1577769684710529
		val patched: String, //1
		val ca: String, //CA
		val agentNo: String, //+918904577948
		val cli: String, //+919591943939
		val startTime: String, //2017-09-06 11:43:45.0
		val duration: Int, //30
		val trunk: String, //0
		val SNo: Int, //1
		val causecode: Int, //16
		val dialType: String, //0
		val filedownload: String, ///voicerecord/RECORDINGS/c2ac/1504678425_1577769684710529_919591943939.mp3
		val filename: String, //1504678425_1577769684710529_919591943939.mp3
		val businessID: String, //9b72e31dac81715466cd580a448cf823
		val prichannel: Int, //0
		val paidcontract: Int, //0
		val dni: String, //+911133190722
		val callType: String //I
)