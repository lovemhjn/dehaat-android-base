package com.dehaat.androidbase.helper

fun Int.ordinal(): String {
    val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    val num = this % 100
    return this.toString() + suffix[if (num in 4..20) 0 else num % 10]
}