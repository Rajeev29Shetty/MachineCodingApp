package com.rajeev.machinecoding.machinecoding.common

import android.util.Log
import java.util.Random
import kotlin.streams.asSequence

fun String.getRandomCharsIncludingString() : List<Char> {
    val givenString = this.toList()
    val randomString = givenString + getRandomString().toList()
    return randomString.shuffled()
}

fun getRandomString(length: Long = 6) : String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return Random().ints(length, 0, source.length)
        .asSequence()
        .map(source::get)
        .joinToString("")
}