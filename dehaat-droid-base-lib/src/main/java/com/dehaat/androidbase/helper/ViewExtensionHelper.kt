package com.dehaat.androidbase.helper

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun View.hideMe() {
    this.visibility = View.GONE
}

fun View.invisibleMe() {
    this.visibility = View.INVISIBLE
}

fun View.changeVisibility(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.getToggleVisibility() = if (this.visibility == View.VISIBLE) View.GONE else View.VISIBLE

fun View.toggleVisibility() = getToggleVisibility().also { visibility = it }

fun View.alterVisibility(show: Boolean) = if (show) showMe() else hideMe()

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun View.setStroke(
    @ColorRes color: Int,
    strokeWidth: Int = 1,
    @IntRange(from = 0x0, to = 0xFF) alpha: Int = 60
) {
    val statusColor = ContextCompat.getColor(context, color)
    val drawable = background as? GradientDrawable
    drawable?.apply {
        setStroke(strokeWidth.dpToPx().toInt(), statusColor)
        setColor(ColorUtils.setAlphaComponent(statusColor, alpha))
    }
}

fun TextView.setStroke(
    @ColorRes color: Int,
    strokeWidth: Int = 1,
    @IntRange(from = 0x0, to = 0xFF) alpha: Int = 60
) {
    val statusColor = ContextCompat.getColor(context, color)
    setTextColor(statusColor)
    val drawable = background as? GradientDrawable
    drawable?.apply {
        setStroke(strokeWidth.dpToPx().toInt(), statusColor)
        setColor(ColorUtils.setAlphaComponent(statusColor, alpha))
    }
}

