package com.dehaat.androidbase.components.ui.listeners

import android.text.Editable
import android.text.TextWatcher

class AfterTextChangedWatcher(val afterTextChanged: (Editable?) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}