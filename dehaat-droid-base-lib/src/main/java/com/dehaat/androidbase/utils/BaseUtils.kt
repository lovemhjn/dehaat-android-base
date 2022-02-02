package com.dehaat.androidbase.utils

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.dehaat.androidbase.AndroidAppBase
import com.dehaat.androidbase.helper.dpToPx
import com.dehaat.androidbase.helper.tryCatch

object BaseUtils {

    fun safeNavigate(
        navController: NavController?,
        action: Int?,
        bundle: Bundle?,
        destinationId: Int?
    ) = tryCatch({// destinationId is null when user clicks from notification
        if (destinationId == null && action != null)
            navController?.navigate(action, bundle)
        // checking destinationId to prevent crash in case of multiple clicks for different destinations
        else if (destinationId != null && navController?.currentDestination?.id == destinationId)
            if (action != null) {
                if (bundle != null)
                    navController.navigate(action, bundle)
                else
                    navController.navigate(action)
            } else // action is null when user presses back button
                navController.navigateUp()
    }, {
        AndroidAppBase.iExceptionListener?.onCatchException(it)
    })

    fun hideKeyboard(context: Context?, view: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (view != null)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getVerticalDivider(spaceHeight: Int = 18, needTopDivider: Boolean = true) =
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val space = spaceHeight.dpToPx().toInt()
                outRect.bottom = space
                if (needTopDivider && parent.getChildLayoutPosition(view) == 0)
                    outRect.top = space
            }
        }

    fun getHorizontalDivider(spaceWidth: Int = 10) = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val space = spaceWidth.dpToPx()
            outRect.left = space.toInt()
        }
    }

    fun getGridDivider(space: Int = 10) = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val childLayoutPosition = parent.getChildLayoutPosition(view)

            outRect.top = if (childLayoutPosition == 0 || childLayoutPosition == 1) {
                space
            } else {
                0
            }

            if (childLayoutPosition % 2 == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                outRect.left = space / 2
                outRect.right = space
            }
            outRect.bottom = space
        }
    }

}