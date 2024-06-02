package com.example.data.test_json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun <D> testJson(
    filePath: String
): List<D> {
    val fileContent = File(filePath).readText()
    val gson = Gson()
    val type = object : TypeToken<List<D>>() {}.type
    return gson.fromJson(fileContent, type)
}