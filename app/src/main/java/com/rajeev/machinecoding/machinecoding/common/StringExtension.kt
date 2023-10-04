package com.rajeev.machinecoding.machinecoding.common

fun String.getRandomCharsIncludingString() : List<Char> {
    val givenString = this.toList()
    val randomString = "qwetrty".toList() // TODO method to generate a random string
    return (givenString + randomString)
}