package com.talisman.app

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.talisman.app.ui.customers.model.Entry
import com.talisman.app.ui.recentcalls.model.CDRJSON

/**
 * Created by Tarun on 12/18/17.
 */
class Converters
{
    @TypeConverter
    fun fromString(value: String): ArrayList<CDRJSON>? {
        val listType = object : TypeToken<ArrayList<CDRJSON>>() {

        }.type
        return Gson().fromJson<ArrayList<CDRJSON>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<CDRJSON>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun fromCustomerString(value: String): List<Entry>? {
        val listType = object : TypeToken<List<Entry>>() {

        }.type
        return Gson().fromJson<List<Entry>>(value, listType)
    }

    @TypeConverter
    fun fromCustomerArrayList(list: List<Entry>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun fromTicketString(value: String): List<com.talisman.app.ui.tickets.model.Entry>? {
        val listType = object : TypeToken<List<com.talisman.app.ui.tickets.model.Entry>>() {

        }.type
        return Gson().fromJson<List<com.talisman.app.ui.tickets.model.Entry>>(value, listType)
    }

    @TypeConverter
    fun fromTicketArrayList(list: List<com.talisman.app.ui.tickets.model.Entry>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}