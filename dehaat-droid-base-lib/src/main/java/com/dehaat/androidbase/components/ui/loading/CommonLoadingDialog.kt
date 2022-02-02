package com.dehaat.androidbase.components.ui.loading

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.agridroid.baselib.databinding.TemplateCommonLoadingDialogBinding

class CommonLoadingDialog private constructor(ctx: Context, private val content: String? = null) :
    Dialog(ctx) {

    private lateinit var binding: TemplateCommonLoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding =
            TemplateCommonLoadingDialogBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)

        val width = (context.resources.displayMetrics.widthPixels * 0.95).toInt()
        window?.let {
            it.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }

        setCanceledOnTouchOutside(false)
        setCancelable(true)
        binding.apply {
            content?.let { tvContent.text = it }
        }

    }

    class Builder constructor(val context: Context) {

        private var content: String? = null
        private var cancelAble: Boolean? = null
        private var onCancelListener: DialogInterface.OnCancelListener? = null
        private var onDismissListener: DialogInterface.OnDismissListener? = null

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setCancelAble(cancelAble: Boolean): Builder {
            this.cancelAble = cancelAble
            return this
        }

        fun setCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun setDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            this.onDismissListener = onDismissListener
            return this
        }

        fun create(): CommonLoadingDialog {
            val dialog = CommonLoadingDialog(context, content)
            val cancel = cancelAble ?: false
            dialog.setCancelable(cancel)
            dialog.setCanceledOnTouchOutside(cancel)
            dialog.setOnCancelListener(onCancelListener)
            dialog.setOnDismissListener(onDismissListener)
            return dialog
        }

        fun createAndShow(): CommonLoadingDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }

    }

}
