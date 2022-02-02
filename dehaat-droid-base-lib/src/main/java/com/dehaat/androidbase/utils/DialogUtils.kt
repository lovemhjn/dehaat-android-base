package com.dehaat.androidbase.utils

import android.app.ProgressDialog
import android.content.Context
import android.view.Window
import com.agridroid.baselib.R

object DialogUtils {

    fun createProgressDialog(
        context: Context?,
        cancelable: Boolean = false,
        message: String? = null
    ) {
        context?.let { ctx ->
            ProgressDialog(ctx, 0).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setMessage(message ?: ctx.getString(R.string.please_wait_msg))
                setCancelable(cancelable)
            }
        }
    }
}