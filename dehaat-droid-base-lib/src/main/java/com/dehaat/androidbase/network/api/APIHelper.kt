package com.dehaat.androidbase.network.api

import com.cleanarch.base.common.ApiExtraInfo
import com.cleanarch.base.entity.result.api.APIResultEntity
import com.dehaat.androidbase.network.intercepters.ApiException
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

typealias APIResultSuccessEntity<D> = APIResultEntity.Success<D>
typealias APIResultErrorException = APIResultEntity.Failure.ErrorException
typealias APIResultErrorFailure = APIResultEntity.Failure.ErrorFailure

suspend fun <D, R> makeAPICall(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> Response<D>,
    parse: (D?) -> R,
    getExtraInfo: (Request?, Response<D>?) -> ApiExtraInfo
) = withContext(coroutineContext) {
    try {
        when (val response = getAPIResponse(coroutineContext, getExtraInfo) { apiCall.invoke() }) {
            is APIResponse.Success -> APIResultSuccessEntity(parse(response.data))
            is APIResponse.Failure.APIException -> APIResultErrorException(response.error, response.apiExtraInfo)
            is APIResponse.Failure.ResponseUnsuccessful -> {
                val (code, errorBody, message) = response.error
                APIResultErrorFailure(code, message, errorBodyToString(errorBody), response.apiExtraInfo)
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
        APIResultEntity.Failure.ErrorException(ex, null)
    }
}

private fun errorBodyToString(errorBody: ResponseBody?) = try {
    errorBody?.string()
} catch (ex: Exception) {
    null
}

suspend fun <B> getAPIResponse(
	coroutineContext: CoroutineContext,
	getExtraInfo: (Request?, Response<B>?) -> ApiExtraInfo,
	apiCall: suspend () -> Response<B>,
): APIResponse<B?> {
    return withContext(coroutineContext) {
        try {
            with(apiCall()) {
                if (isSuccessful) {
                    APIResponse.Success(body())
                } else {
                    APIResponse.Failure.ResponseUnsuccessful(
                        toUnsuccessfulResponse(this),
                        getExtraInfo(raw().request, this)
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            if(ex is ApiException){
                APIResponse.Failure.APIException(ex, getExtraInfo(ex.request, null))
            } else {
                APIResponse.Failure.APIException(ex)
            }
        }
    }
}

fun toUnsuccessfulResponse(response: Response<*>) = with(response) {
    DataResponseUnsuccessful(
        code(),
        errorBody(),
        message()
    )
}

