package com.example.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListToString(list: List<Int>): String =
        if (list.isEmpty()) ""
        else list.sorted().joinToString(",")

    @TypeConverter
    fun fromStringToList(string: String): List<Int> =
        if (string == "") emptyList()
        else string.split(",").map { it.toInt() }
}
