package com.agridroid.baselib.extensions

import androidx.annotation.UiThread
import com.agridroid.baselib.callbacks.IProgressViewListener
import com.cleanarch.base.presentation.ui.UIState

@UiThread
fun <D> UIState<D>.processWithProgress(
    iProgressViewListener: IProgressViewListener,
    successBlock: (data: D) -> Unit,
    failureBlock: (Int, String) -> Unit = { _, _ -> }
) = when (this) {
    UIState.DEFAULT -> iProgressViewListener.hideProgressView()
    UIState.LOADING -> iProgressViewListener.showProgressView()
    is UIState.Loaded -> {
        iProgressViewListener.hideProgressView()
        when (this) {
            is UIState.Loaded.Success -> successBlock(this.data)
            is UIState.Loaded.Failure -> failureBlock(this.messageViewType, this.errorMessage)
        }
    }
}