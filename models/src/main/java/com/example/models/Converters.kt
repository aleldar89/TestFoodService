package com.example.models

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromListToString(list: List<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<Int> {
        return Gson().fromJson(string, Array<Int>::class.java).toList()
    }
}
