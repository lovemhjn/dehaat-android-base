package com.dehaat.androidbase.helper

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.agridroid.baselib.R
import com.dehaat.androidbase.AndroidAppBase
import com.dehaat.androidbase.utils.IntentUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

inline fun Fragment.ifFragmentSafe(block: (activity: FragmentActivity) -> Unit) {
    if (this.isAdded && this.activity != null) {
        block(activity!!)
    }
}

fun Fragment.openActivity(activity: Class<*>) = ifFragmentSafe {
    startActivity(Intent(it, activity))
}

fun Fragment.openDialer(phoneNumber: String?) = if (!phoneNumber.isNullOrBlank()) {
    val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    tryCatch({
        startActivity(i)
    }, {
        context?.showToast(it.message ?: getString(R.string.unknown_error))
    })
} else {
    context?.showToast(getString(R.string.error_phone_number_not_found))
}

fun Fragment.openGoogleMap(coordinates: List<Double>?) = ifFragmentSafe {
    tryCatch({
        if (coordinates?.size == 2) {
            val uri = Uri.parse(
                String.format(
                    Locale.ENGLISH,
                    "http://maps.google.com/maps?daddr=%f,%f",
                    coordinates[1],
                    coordinates[0]
                )
            )
            val mapIntent = IntentUtils.getActionViewIntent(uri)
            mapIntent.setPackage(IntentUtils.PACKAGE_GOOGLE_MAP)
            if (mapIntent.resolveActivity(it.packageManager) != null) {
                startActivity(mapIntent)
            } else {
                AndroidAppBase.iExceptionListener?.onCatchException(Exception(getString(R.string.error_map_not_found)))
                context?.showToast(R.string.error_open_map)
            }
        } else {
            context?.showToast(R.string.error_location)
        }
    }, {
        AndroidAppBase.iExceptionListener?.onCatchException(it)
    })
}

fun Fragment.openWhatsApp(phoneNumber: String?) = if (!phoneNumber.isNullOrBlank()) {

    try {
        val url = "https://api.whatsapp.com/send?phone=91$phoneNumber"
        val pm: PackageManager = context!!.packageManager
        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    } catch (e: PackageManager.NameNotFoundException) {
        context?.showToast(getString(R.string.whatsapp_not_installed))
        e.printStackTrace()
    }

} else {
    context?.showToast(getString(R.string.error_phone_number_not_found))
}

fun AppCompatActivity.setStartDestination(
    @IdRes fragmentContainerViewId: Int,
    @NavigationRes navGraphId: Int,
    @IdRes startDestId: Int
) =
    (supportFragmentManager.findFragmentById(fragmentContainerViewId) as? NavHostFragment)?.navController?.apply {
        graph = navInflater.inflate(navGraphId).apply { setStartDestination(startDestId) }
    }

fun AppCompatActivity.setStartDestinationWithBundle(
    @IdRes fragmentContainerViewId: Int,
    @NavigationRes navGraphId: Int,
    @IdRes startDestId: Int,
    bundle: Bundle? = null
) =
    (supportFragmentManager.findFragmentById(fragmentContainerViewId) as? NavHostFragment)?.navController?.apply {
        setGraph(navInflater.inflate(navGraphId).apply { setStartDestination(startDestId) }, bundle)
    }

fun <T> Fragment.launchWhenStartedWithViewLifecycleOwner(flow: Flow<T>, flowCollectionBlock: suspend (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.collectLatest {
            flowCollectionBlock(it)
        }
    }

fun <T> Fragment.launchWithViewLifecycleOwner(flow: Flow<T>, flowCollectionBlock: suspend (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collectLatest {
            flowCollectionBlock(it)
        }
    }