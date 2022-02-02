package com.dehaat.androidbase.components

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleEventLiveData<T> : MutableLiveData<ConsumableValue<T>>() {

    fun postSingleEventValue(value: T) {
        super.postValue(ConsumableValue(value))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in ConsumableValue<T>>) {
        super.observe(owner, observer)
    }
}

class ConsumableValue<T>(private val data: T) {

    private var consumed = false

    fun consume(block: (T?) -> Unit) {
        if (!consumed) {
            consumed = true
            block(data)
        }
    }

}