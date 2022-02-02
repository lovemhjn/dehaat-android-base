package com.dehaat.androidbase.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    const val YYYY_D_MM_D_DD = "yyyy-MM-dd"
    const val dd_MMM_yyy = "dd-MMM-yyyy"
    const val EEE_dd_MMM = "EEE, dd MMM"
}

object DateUtils {
    val defaultLocale = Locale("en", "in")

    fun fromToDateFormat(
        fromFormat: String,
        toFormat: String,
        date: String,
        locale: Locale = defaultLocale
    ) =
        SimpleDateFormat(fromFormat, locale).parse(date)?.let {
            SimpleDateFormat(toFormat, locale).format(it)
        }

    fun getDateInFormat(
        toFormat: String,
        date: Date = Date(),
        locale: Locale = defaultLocale
    ) =
        SimpleDateFormat(toFormat, locale).format(date)

    fun getDateInFormat(
        toFormat: String,
        timeInMilliSecond: Long,
        locale: Locale = defaultLocale
    ) =
        SimpleDateFormat(toFormat, locale).format(timeInMilliSecond)

    fun getCurrenDateInFormat(
        toFormat: String,
        locale: Locale = defaultLocale
    ) =
        SimpleDateFormat(toFormat, locale).format(Calendar.getInstance().timeInMillis)

    fun getDateInFormat(
        toFormat: String,
        timeInSecond: Float,
        locale: Locale = defaultLocale
    ): String =
        SimpleDateFormat(toFormat, locale).format(timeInSecond * 1000)
}