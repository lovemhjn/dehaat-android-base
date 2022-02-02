package com.dehaat.androidbase.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.EditText
import android.widget.TextView
import java.util.*

object TextUtilities {
    private val locale = Locale("en", "in")

    fun underLineText(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    /**
     * Method to remove comma from editText to POST value
     */
    fun removeComma(value: String): String {
        return if (value.contains(","))
            value.replace(",".toRegex(), "")
        else
            value
    }

    @JvmStatic
    fun isNullCase(test: String?): Boolean {
        return test == null || test.trim { it <= ' ' } == "" || test == "false" ||
                test == "null" || test == "False"
    }

    fun isNullCaseFloat(test: String): Boolean {
        return isNullCase(test) || test == "." || test.toFloat() == 0f
    }

    fun isNullCase(test: Int?): Boolean {
        return test == null || test == 0
    }

    //get icon from fontAwesome
    fun getSolidIcon(context: Context?): Typeface {
        return Typeface.createFromAsset(context?.assets, "Solid-900.otf")
    }

    fun isIfscCodeValid(ifscCode: String): Boolean {
        val regExp = "^[A-Za-z0-9]{4}[0][0-9A-Za-z]{6}$"
        return if (ifscCode.isNotEmpty())
            ifscCode.matches(regExp.toRegex())
        else
            false
    }

    private fun isVpaValid(vpa: String): Boolean {
        val regExp = "^[a-zA-Z0-9_.]{3,}@[a-zA-Z]{3,}$"
        return if (vpa.isNotEmpty())
            vpa.matches(regExp.toRegex())
        else
            false
    }

    fun isVpaOrNumValid(vpa: String): Boolean {
        val regexStr = "^[0-9]{4}$"
        return if (vpa.isNotEmpty())
            vpa.matches(regexStr.toRegex()) || isVpaValid(vpa)
        else
            false
    }

    fun addCommaInEditText(s: Editable?, amount: EditText) {
        if (s.isNullOrBlank()) return
        try {
            var originalString = s.toString()
            val longVal: Long
            originalString = removeComma(originalString)
            longVal = originalString.toLong()
            val formattedString = NumberUtilities.formatLong(longVal)
            amount.setText(formattedString)
            amount.setSelection(amount.text.length)
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }
    }

    fun getInitials(name: String?): String {
        return if (isNullCase(name)) ""
        else
            name!!.replace("([^\\s])[^\\s]+".toRegex(), "$1")
                .replace("\\s".toRegex(), "").toUpperCase(locale)
    }

    fun setSpanString(
        string1: String?, string2: String?, textView: TextView?,
        string1Color: Int? = null
    ) {
        if (string1 == null || string2 == null || textView == null) return
        val builder = SpannableStringBuilder()
        val txtSpannable = SpannableString(string2)
        val boldSpan = StyleSpan(Typeface.BOLD)
        txtSpannable.setSpan(boldSpan, 0, string2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (string1Color != null) {
            val string1Spannable = SpannableString(string1)
            val colorSpan = ForegroundColorSpan(string1Color)
            string1Spannable.setSpan(
                colorSpan,
                0,
                string1.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.append(string1Spannable)
        } else {
            builder.append(string1)
        }
        builder.append(txtSpannable)
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }

    fun showMultipleBoldSpans(
        textView: TextView, boldStrings: ArrayList<String?>,
        normalStrings: ArrayList<String>
    ) {
        val builder = SpannableStringBuilder()
        val spanFlag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        builder.apply {
            if (normalStrings.size == boldStrings.size) {
                normalStrings.indices.forEach { index ->
                    append(normalStrings[index])
                    val textSpannable = SpannableString(boldStrings[index])
                    textSpannable.setSpan(
                        StyleSpan(Typeface.BOLD), 0, boldStrings[index]?.length
                            ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    append(textSpannable)
                }
            }
        }
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }
}