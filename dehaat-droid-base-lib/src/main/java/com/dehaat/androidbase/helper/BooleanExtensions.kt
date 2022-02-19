package com.dehaat.androidbase.helper

fun Boolean?.isTrue() = this == true

fun Boolean?.isFalse() = this == false

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true