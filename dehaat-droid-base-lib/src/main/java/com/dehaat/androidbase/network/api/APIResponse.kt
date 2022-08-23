package com.dehaat.androidbase.network.api

import com.cleanarch.base.common.ApiExtraInfo
import com.cleanarch.base.data.IData

sealed class APIResponse<out T> : APIState.PostExecute<T>(), IData {
    data class Success<D>(val data: D?) : APIResponse<D>()
    sealed class Failure : APIResponse<Nothing>() {
        data class ResponseUnsuccessful(val error: DataResponseUnsuccessful, val apiExtraInfo: ApiExtraInfo? = null) : Failure()
        data class APIException(val error: Exception, val apiExtraInfo: ApiExtraInfo? = null) : Failure()
    }
}