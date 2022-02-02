package com.dehaat.androidbase.helper

inline fun <T> List<T>?.notNullOrEmpty(block: (List<T>) -> Unit): List<T>? {

    if (!this.isNullOrEmpty()) {
        block(this)
        return this
    }
    return null
}

fun <T> List<T>?.sizeMoreThanOne() = !this.isNullOrEmpty() && this.size > 1

inline fun <T> List<T>?.notNullOrEmptyForEach(block: (T) -> Unit) = if (!this.isNullOrEmpty()) {
    this.forEach {
        block(it)
    }
    this
} else {
    null
}

inline fun tryCatch(block: () -> Unit) = try {
    block()
} catch (e: Exception) {
    e.printStackTrace()
}

inline fun <R> tryCatchWithReturn(fallBack: R, block: () -> R) = try {
    block()
} catch (e: Exception) {
    e.printStackTrace()
    fallBack
}

inline fun tryCatch(tryBlock: () -> Unit, exceptionBlock: (e: Exception) -> Unit = {}) = try {
    tryBlock()
} catch (e: Exception) {
    exceptionBlock(e)
}

inline fun <R> tryCatch(tryBlock: () -> R, exceptionBlock: (e: Exception) -> R) = try {
    tryBlock()
} catch (e: Exception) {
    exceptionBlock(e)
}

fun String?.toBlankIfNull() = this ?: ""