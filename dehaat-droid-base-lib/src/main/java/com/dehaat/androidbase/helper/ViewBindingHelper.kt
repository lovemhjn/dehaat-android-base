package com.dehaat.androidbase.helper

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding

fun ViewBinding.showToast(
    message: String,
    length: Int = Toast.LENGTH_SHORT
) {
    root.context.showToast(message, length)
}

fun ViewBinding.showToast(
    @StringRes message: Int,
    length: Int = Toast.LENGTH_SHORT
) {
    root.context.showToast(message, length)
}