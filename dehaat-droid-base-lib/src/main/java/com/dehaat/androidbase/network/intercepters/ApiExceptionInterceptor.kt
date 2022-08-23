package com.dehaat.androidbase.network.intercepters

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ApiExceptionInterceptor(
) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		return try {
			chain.proceed(chain.request())
		} catch (exception: IOException) {
			throw ApiException(chain.request(), exception.message)
		}
	}
}

data class ApiException(val request: Request, override val message: String?) : IOException()