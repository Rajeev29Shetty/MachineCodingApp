package com.rajeev.machinecoding.machinecoding.util

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

fun ReadJsonFromAsset(context: Context, path: String): String {
    try {
        val file = context.assets.open("$path")
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        val jsonString = stringBuilder.toString()
        return jsonString
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}