package com.dehaat.androidbase.coroutine

import kotlinx.coroutines.Dispatchers

class Dispatchers : IDispatchers {
    override val io
        get() = Dispatchers.IO
    override val main
        get() = Dispatchers.Main
    override val default
        get() = Dispatchers.Default
    override val unConfined
        get() = Dispatchers.Unconfined
}