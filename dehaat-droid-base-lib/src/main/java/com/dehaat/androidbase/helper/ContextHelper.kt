package com.dehaat.androidbase.helper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.startActivityAndClearStack(clazz: Class<*>, extras: Bundle?) {
    val intent = Intent(this, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent)
}

fun Context.showToast(
    message: String,
    length: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, message, length).show()

fun Context.showToast(
    @StringRes message: Int,
    length: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, message, length).show()

fun Context.toColor(
    @ColorRes color: Int
) = ContextCompat.getColor(this, color)