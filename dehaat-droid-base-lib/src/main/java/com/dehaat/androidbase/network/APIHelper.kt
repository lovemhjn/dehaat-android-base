package com.dehaat.androidbase.network

import com.cleanarch.base.common.ApiExtraInfo
import com.dehaat.androidbase.callbacks.IProgressViewListener
import com.dehaat.androidbase.helper.tryCatchWithReturn
import com.dehaat.androidbase.network.intercepters.ApiException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Request
import retrofit2.Response

suspend fun <B> getAPIResponse(
	apiCall: suspend () -> Response<B>,
	getExtraInfo: (Request?, Response<B>?) -> ApiExtraInfo
): APIResponse<B> {
	return try {
		with(apiCall()) {
			if (isSuccessful) {
				APIResponse.Success(body())
			} else {
				APIResponse.Failure.ResponseUnsuccessful(
					DataResponseUnsuccessful(
						errorBody(),
						code(),
						message()
					),
					getExtraInfo(this.raw().request, this)
				)
			}
		}

	} catch (error: Exception) {
		error.printStackTrace()
		if(error is ApiException){
			APIResponse.Failure.APIException(error.message, error, getExtraInfo(error.request, null))
		} else {
			APIResponse.Failure.APIException(error.message, error)
		}
	}
}

suspend fun <B> getAPIStates(apiCall: suspend () -> Response<B>?): Flow<APIState<B>> {
	return flow {

		emit(APIState.PreExecute)

		val state = try {
			apiCall()?.let { response ->
				with(response) {
					if (isSuccessful) {
						APIResponse.Success(body())
					} else {
						APIResponse.Failure.ResponseUnsuccessful(
							DataResponseUnsuccessful(
								errorBody(),
								code(),
								message()
							)
						)

					}
				}
			} ?: run {
				APIResponse.Failure.NullResponse
			}

		} catch (error: Exception) {
			error.printStackTrace()
			APIResponse.Failure.APIException(error.message, error)
		}
		emit(state)
	}
}

fun <D> APIState<D>.processWithProgress(
	iProgressViewListener: IProgressViewListener,
	successBlock: (data: D?) -> Unit,
	failureBlock: (failureResponse: APIResponse.Failure) -> Unit = {},
	preExecuteBlock: () -> Unit = {}
) = when (this) {
	APIState.PreExecute -> {
		iProgressViewListener.showProgressView()
		preExecuteBlock()
	}
	is APIState.PostExecute -> {
		iProgressViewListener.hideProgressView()
		when (this) {
			is APIResponse.Success -> {
				successBlock(this.data)
			}
			is APIResponse.Failure -> {
				failureBlock(this)
			}
		}
	}

}

fun <D> APIState<D>.process(
	successBlock: (data: D?) -> Unit,
	failureBlock: (failureResponse: APIResponse.Failure) -> Unit = {},
	preExecuteBlock: () -> Unit = {}
) = when (this) {
	APIState.PreExecute -> {
		preExecuteBlock()
	}
	is APIResponse.Success -> {
		successBlock(this.data)
	}
	is APIResponse.Failure -> {
		failureBlock(this)
	}
}

fun <D> APIResponse<D>.process(
	successBlock: (data: D?) -> Unit,
	failureBlock: (failureResponse: APIResponse.Failure) -> Unit = {}
) {
	when (this) {
		is APIResponse.Success -> {
			successBlock(this.data)
		}
		is APIResponse.Failure -> {
			failureBlock(this)
		}
	}
}

fun APIResponse.Failure.process(
	httpErrorsBlock: (DataResponseUnsuccessful) -> Unit = { _ -> },
	apiCallExceptionBlock: (String?, Exception, APIResponse.Failure.APIException) -> Unit = { _, _, _ -> },
	nullResponseBlock: () -> Unit = { },
) = when (this) {
	is APIResponse.Failure.ResponseUnsuccessful -> {
		httpErrorsBlock(this.error)
	}
	is APIResponse.Failure.APIException -> {
		with(this) {
			apiCallExceptionBlock(this.errorMsg, this.error, this)
		}
	}
	APIResponse.Failure.NullResponse -> {
		nullResponseBlock()
	}
}

fun DataResponseUnsuccessful.getAnyError(): String {

	val parsedErrorMessage = tryCatchWithReturn(errorMsgResponse) {
		errorBody?.let { it ->
			Gson().fromJson(
				it.string(),
				com.google.gson.JsonObject::class.java
			)?.getAsJsonObject("error")?.get("message")?.asString
		} ?: errorMsgResponse
	}

	return if (parsedErrorMessage.isBlank()) {
		"Server Error"
	} else {
		parsedErrorMessage
	}

}