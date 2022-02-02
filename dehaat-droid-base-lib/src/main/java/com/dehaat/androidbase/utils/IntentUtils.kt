package com.dehaat.androidbase.utils

import android.content.Intent
import android.net.Uri

object IntentUtils {
    const val PACKAGE_GOOGLE_MAP = "com.google.android.apps.maps"
    fun getActionViewIntent(uri: Uri) = Intent(Intent.ACTION_VIEW, uri)
}