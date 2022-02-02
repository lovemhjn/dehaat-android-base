package com.dehaat.androidbase.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unConfined: CoroutineDispatcher
}