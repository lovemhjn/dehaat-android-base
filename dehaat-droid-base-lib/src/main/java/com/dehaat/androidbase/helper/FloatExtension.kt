package com.dehaat.androidbase.helper

fun Float.toFloatMinRounded() = this.minus(this % 1)