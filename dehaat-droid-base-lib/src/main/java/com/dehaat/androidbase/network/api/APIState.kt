package com.dehaat.androidbase.network.api

import com.cleanarch.base.data.IData

sealed class APIState<out D>: IData {
    object PreExecute : APIState<Nothing>()
    sealed class PostExecute<out D> : APIState<D>()
}