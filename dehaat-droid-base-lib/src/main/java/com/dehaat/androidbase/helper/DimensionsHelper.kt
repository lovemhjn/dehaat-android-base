package com.dehaat.androidbase.helper

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.Dimension

@JvmOverloads
@Dimension(unit = Dimension.PX)
fun Number.dpToPx(
    metrics: DisplayMetrics = Resources.getSystem().displayMetrics
): Float {
    val pixelValue =  toFloat() * metrics.density
    return if(pixelValue.isNaN()) {
        toFloat()
    } else {
        pixelValue
    }
}

@JvmOverloads
@Dimension(unit = Dimension.DP)
fun Number.pxToDp(
    metrics: DisplayMetrics = Resources.getSystem().displayMetrics
): Float {
    val dpValue = toFloat() / metrics.density
    return if(dpValue.isNaN()) {
        toFloat()
    } else {
        dpValue
    }
}