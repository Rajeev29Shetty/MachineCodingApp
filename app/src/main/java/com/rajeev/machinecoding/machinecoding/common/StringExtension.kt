package com.rajeev.machinecoding.machinecoding.common

fun String.getRandomCharsIncludingString() : List<Char> {
    val givenString = this.toList()
    val randomString = givenString + "qwetrty".toList() // TODO method to generate a random string
    return randomString.shuffled()
}