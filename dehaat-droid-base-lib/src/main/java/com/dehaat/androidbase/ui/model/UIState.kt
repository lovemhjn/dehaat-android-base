package com.dehaat.androidbase.ui.model

import com.dehaat.common.MessageType

sealed class UIState<out D> {
    object DEFAULT : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    sealed class Loaded<out D> : UIState<D>() {
        data class Success<D>(val data: D) : Loaded<D>()
        data class Failure(@MessageType val type: Int, val errorMessage: String) : Loaded<Nothing>()
    }
}