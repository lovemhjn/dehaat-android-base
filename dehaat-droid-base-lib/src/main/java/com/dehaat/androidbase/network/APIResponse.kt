package com.dehaat.androidbase.network

import com.dehaat.androidbase.network.model.BaseResponseWithData
import okhttp3.ResponseBody

typealias APIStateWithBaseResponseList<E> = APIState<BaseResponseWithData<ArrayList<E>>>
typealias APIStateWithBaseResponse<D> = APIState<BaseResponseWithData<D>>

sealed class APIState<out D> {
    object PreExecute : APIState<Nothing>()
    sealed class PostExecute<out D> : APIState<D>()
}

sealed class APIResponse<out D> : APIState.PostExecute<D>() {

    data class Success<D>(val data: D?) : APIResponse<D>()

    sealed class Failure : APIResponse<Nothing>() {

        object NullResponse : Failure()

        data class ResponseUnsuccessful(val error: DataResponseUnsuccessful) : Failure()

        data class APIException(val errorMsg: String?, val error: Exception) :
            Failure()
    }
}

data class DataResponseUnsuccessful(
    val errorBody: ResponseBody?,
    val errorCode: Int,
    val errorMsgResponse: String,
)