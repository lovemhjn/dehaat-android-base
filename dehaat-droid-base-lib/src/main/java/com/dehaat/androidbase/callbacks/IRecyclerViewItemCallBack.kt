package com.dehaat.androidbase.callbacks

import androidx.recyclerview.widget.RecyclerView

fun interface IRecyclerViewItemCallBack<T : RecyclerView.ViewHolder, D> {
    fun onBindOfViewHolder(holder: T, positionAndData: Pair<Int, D>)
}