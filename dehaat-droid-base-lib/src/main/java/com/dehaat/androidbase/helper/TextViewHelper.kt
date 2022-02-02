package com.dehaat.androidbase.helper

import android.widget.TextView
import androidx.annotation.DrawableRes

fun TextView.removeDrawable() = setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

fun TextView.setDrawableLeft(@DrawableRes drawableRes: Int) =
    setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)

fun TextView.setDrawableRight(@DrawableRes drawableRes: Int) =
    setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)