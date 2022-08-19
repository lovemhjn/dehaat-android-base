package com.dehaat.androidbase.network.intercepters

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.SocketTimeoutException

class NoConnectionInterceptor(
) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		return try {
			chain.proceed(chain.request())
		} catch (timeoutException: SocketTimeoutException) {
			throw ApiTimeoutException(chain.request())
		}
	}
}

data class ApiTimeoutException(val request: Request) : SocketTimeoutException()