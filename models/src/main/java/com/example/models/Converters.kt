package com.example.models

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

//    @TypeConverter
//    fun fromListToString(list: List<Int>): String? = Gson().toJson(list)
//
//    @TypeConverter
//    fun fromStringToList(string: String): List<Int> =
//        Gson().fromJson(string, Array<Int>::class.java).toList()

    @TypeConverter
    fun fromListToString(list: List<Int>): String =
        if (list.isEmpty()) ""
        else list.sorted().joinToString(",")

    @TypeConverter
    fun fromStringToList(string: String): List<Int> =
        if (string == "") emptyList()
        else string.split(",").map { it.toInt() }
}
