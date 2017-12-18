package com.talisman.app.ui.recentcalls.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.talisman.app.BaseResponse

/**
 * Created by Tarun on 11/21/17.
 */

@Entity(tableName="RecentCallResponse")
data class RecentCallResponse ( @PrimaryKey(autoGenerate = true)
								  var id : Int? = 0,
		var CDRJSON: ArrayList<CDRJSON>
): BaseResponse()


data class CDRJSON(
		var attempt: Int, //1
		var endtype: String, //CALL-DISCONNECT
		var callNo: String, //1577769684710529
		var patched: String, //1
		var ca: String, //CA
		var agentNo: String, //+918904577948
		var cli: String, //+919591943939
		var startTime: String, //2017-09-06 11:43:45.0
		var duration: Int, //30
		var trunk: String, //0
		var SNo: Int, //1
		var causecode: Int, //16
		var dialType: String, //0
		var filedownload: String, ///voicerecord/RECORDINGS/c2ac/1504678425_1577769684710529_919591943939.mp3
		var filename: String, //1504678425_1577769684710529_919591943939.mp3
		var businessID: String, //9b72e31dac81715466cd580a448cf823
		var prichannel: Int, //0
		var paidcontract: Int, //0
		var dni: String, //+911133190722
		var callType: String //I
)