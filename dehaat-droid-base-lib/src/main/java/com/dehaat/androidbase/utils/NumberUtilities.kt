package com.dehaat.androidbase.utils

import java.text.NumberFormat
import java.util.*

object NumberUtilities {
    val locale = Locale("en", "in")
    fun formatDecimal(value: Double?): String {
        val formatter = NumberFormat.getNumberInstance(locale)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter.format(value ?: 0.0)
    }

    fun formatArea(value: Double?): String {
        val formatter = NumberFormat.getNumberInstance(locale)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 7
        return formatter.format(value ?: 0.0)
    }

    /**
     * Method to display prices with commas at required places according to Indian numeric system
     */
    fun formatLong(value: Long): String {
        val formatter = NumberFormat.getNumberInstance(locale)
        return formatter.format(value)
    }

    fun convertToTwoDigits(str: String): String {
        return if (str.length == 1) "0$str" else str
    }
}