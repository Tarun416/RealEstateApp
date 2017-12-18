package com.talisman.app

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
}