package com.meghdut.news.network

import com.meghdut.news.MyApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class AutoRetryRequest {
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    companion object {
        private var autoRetryRequest: AutoRetryRequest? = null
        fun getInstance(): AutoRetryRequest? {
            synchronized(AutoRetryRequest::class.java) {
                if (autoRetryRequest == null) {
                    autoRetryRequest = AutoRetryRequest()
                }
                return autoRetryRequest
            }
        }
    }

    fun addAutoRetryIntercept(chain: Interceptor.Chain): Response? {
        var builder1 = chain.request().newBuilder()

        val response = chain.proceed(builder1.build())

        if (response.code() == 401) {
            MyApplication.getInstance()?.logoutFromApp()
            return response
        } else if (response.code() == 200) {
            return response
        } else {
            if (response.code() == 203) {
                /*RefreshToken*/

                lock.withLock {
                    condition.await()
                }
            }
        }
        builder1 = chain.request().newBuilder()
        return chain.proceed(builder1.build())
    }
}