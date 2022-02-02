package com.dehaat.androidbase.helper

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.updateItemDecoration(decoration: RecyclerView.ItemDecoration) {
    this.removeItemDecoration(decoration)
    this.addItemDecoration(decoration)
}