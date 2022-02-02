package com.dehaat.androidbase

object AndroidAppBase {

    var iExceptionListener: IExceptionListener? = null

    fun setExceptionListener(iExceptionListener: IExceptionListener) {
        this.iExceptionListener = iExceptionListener
    }
}

abstract class IExceptionListener() {

    abstract fun onCatchException(e: Exception)
}