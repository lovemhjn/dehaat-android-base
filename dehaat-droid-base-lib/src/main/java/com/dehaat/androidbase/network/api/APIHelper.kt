package com.dehaat.androidbase.network.api

import com.cleanarch.base.entity.result.api.APIResultEntity
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

typealias APIResultSuccessEntity<D> = APIResultEntity.Success<D>
typealias APIResultErrorException = APIResultEntity.Failure.ErrorException
typealias APIResultErrorFailure = APIResultEntity.Failure.ErrorFailure

suspend fun <D, R> makeAPICall(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> Response<D>,
    parse: (D?) -> R
) = withContext(coroutineContext) {
    try {
        when (val response = getAPIResponse(coroutineContext) { apiCall.invoke() }) {
            is APIResponse.Success -> APIResultSuccessEntity(parse(response.data))
            is APIResponse.Failure.APIException -> APIResultErrorException(response.error)
            is APIResponse.Failure.ResponseUnsuccessful -> {
                val (code, errorBody, message) = response.error
                APIResultErrorFailure(code, message, errorBodyToString(errorBody))
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
        APIResultEntity.Failure.ErrorException(ex)
    }
}

private fun errorBodyToString(errorBody: ResponseBody?) = try {
    errorBody?.string()
} catch (ex: Exception) {
    null
}

suspend fun <B> getAPIResponse(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> Response<B>
): APIResponse<B?> {
    return withContext(coroutineContext) {
        try {
            with(apiCall()) {
                if (isSuccessful) {
                    APIResponse.Success(body())
                } else {
                    APIResponse.Failure.ResponseUnsuccessful(
                        toUnsuccessfulResponse(this)
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            APIResponse.Failure.APIException(ex)
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

