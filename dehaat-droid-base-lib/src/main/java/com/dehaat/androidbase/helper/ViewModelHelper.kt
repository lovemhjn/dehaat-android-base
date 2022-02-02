package com.dehaat.androidbase.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dehaat.androidbase.network.APIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <R> ViewModel.callAPI(call: suspend () -> Flow<APIState<R>>, onCollect: (APIState<R>) -> Unit) {
    viewModelScope.launch {
        call.invoke().collect {
            onCollect(it)
        }
    }
}

fun <R> ViewModel.callAPI(call: suspend () -> Flow<APIState<R>>, liveData: MutableLiveData<APIState<R>>) {
    viewModelScope.launch {
        call.invoke().collect {
            liveData.value = it
        }
    }
}

fun <R> ViewModel.callInViewModelScope(block: suspend () -> R) {
    viewModelScope.launch {
        block()
    }
}

fun <R> ViewModel.callAPIWithFlow(call: suspend () -> Flow<APIState<R>>, onCollect: suspend(APIState<R>) -> Unit) {
    viewModelScope.launch {
        call.invoke().collect {
            onCollect(it)
        }
    }
}