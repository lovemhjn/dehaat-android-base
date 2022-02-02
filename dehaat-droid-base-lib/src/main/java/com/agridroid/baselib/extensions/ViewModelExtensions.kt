package com.agridroid.baselib.extensions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.lifecycle.viewModelScope
import com.cleanarch.base.entity.result.api.APIResultEntity
import com.cleanarch.base.presentation.ui.UIState
import kotlinx.coroutines.launch

typealias APIResultErrorException = APIResultEntity.Failure.ErrorException
typealias APIResultErrorFailure = APIResultEntity.Failure.ErrorFailure

suspend fun <T, R> ViewModel.callAndGetUIState(
    call: suspend () -> APIResultEntity<R>,
    parseSuccessValue: suspend (R) -> T,
    parseErrorException: (APIResultErrorException) -> UIState.Loaded.Failure = { it.toCommonUIStateFailure() },
    parseErrorFailure: (APIResultErrorFailure) -> UIState.Loaded.Failure = { it.toCommonUIStateFailure() }
) = call.invoke().toUIState(parseSuccessValue, parseErrorException, parseErrorFailure)

fun <T, R> ViewModel.callAndEmitUIState(
    flow: MutableSharedFlow<UIState<T>>,
    call: suspend () -> APIResultEntity<R>,
    parseSuccessValue: suspend (R) -> T,
    parseErrorException: (APIResultErrorException) -> UIState.Loaded.Failure = { it.toCommonUIStateFailure() },
    parseErrorFailure: (APIResultErrorFailure) -> UIState.Loaded.Failure = { it.toCommonUIStateFailure() }
) = viewModelScope.launch {
    flow.emit(UIState.LOADING)
    val result = call.invoke()
    val loaded = result.toUIState(parseSuccessValue, parseErrorException, parseErrorFailure)
    flow.emit(loaded)
}