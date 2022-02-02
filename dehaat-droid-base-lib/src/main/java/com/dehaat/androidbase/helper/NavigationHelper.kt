package com.dehaat.androidbase.helper

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

fun View.safeNavigate(navDirections: NavDirections) {
    tryCatch {
        this.findNavController().navigate(navDirections)
    }
}

fun Fragment.safePopUp() {
    tryCatch {
        findNavController().popBackStack()
    }
}

fun Fragment.safeNavigateUp() {
    tryCatch {
        findNavController().navigateUp()
    }
}

fun Fragment.safeNavigate(navDirections: NavDirections) {
    tryCatch {
        findNavController().navigate(navDirections)
    }
}

fun ViewBinding.safeNavigate(navDirections: NavDirections) {
    tryCatch {
        root.safeNavigate(navDirections)
    }
}