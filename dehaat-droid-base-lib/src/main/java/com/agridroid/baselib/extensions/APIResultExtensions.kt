package com.agridroid.baselib.extensions

import com.cleanarch.base.entity.result.api.APIResultEntity
import com.cleanarch.base.presentation.ui.MessageViewType
import com.cleanarch.base.presentation.ui.UIState


suspend fun <R, T> APIResultEntity<R>.toUIState(
    parseSuccessValue: suspend (R) -> T,
    parseErrorException: (APIResultErrorException) -> UIState.Loaded.Failure,
    parseErrorFailure: (APIResultErrorFailure) -> UIState.Loaded.Failure
) = when (this) {
    is APIResultEntity.Success -> {
        val lots = parseSuccessValue(data)
        UIState.Loaded.Success(lots)
    }
    is APIResultEntity.Failure -> when (this) {
        is APIResultErrorException -> parseErrorException(this)
        is APIResultErrorFailure -> parseErrorFailure(this)
    }
}

fun APIResultErrorException.toUIStateFailure(
    @MessageViewType messageViewType: Int,
    parseError: (APIResultErrorException) -> String
) = UIState.Loaded.Failure(messageViewType, parseError(this))

fun APIResultErrorFailure.toUIStateFailure(
    @MessageViewType messageViewType: Int,
    parseError: (APIResultErrorFailure) -> String
) = UIState.Loaded.Failure(messageViewType, parseError(this))

fun APIResultErrorException.toCommonUIStateFailure() =
    toUIStateFailure(MessageViewType.TOAST) {
        it.exceptionError.message ?: "Api execution Error"
    }

fun APIResultErrorFailure.toCommonUIStateFailure() =
    toUIStateFailure(MessageViewType.TOAST) {
        it.responseMessage
    }