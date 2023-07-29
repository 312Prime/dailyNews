package com.example.dailynews.tools.exceptionManager

import com.example.dailynews.tools.logger.Logger
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ExceptionManager {
    /** 오류 로그 */
    fun log(throwable: Throwable) {
        when (throwable) {
            is TimeoutException,
            is UnknownHostException,
            is IOException -> true
            else -> true
        }.also {
            if (it) {
                Logger.error(throwable)
            }
        }
    }

    /** 재시도 가능한 오류 */
    fun isRetry(throwable: Throwable): Boolean {
        return when (throwable) {
            is TimeoutException,
            is UnknownHostException,
            is IOException -> true

            else -> false
        }
    }

    suspend fun delayRetry(
        throwable: Throwable,
        timeMillis: Long = 500,
    ): Boolean {
        delay(timeMillis)
        return isRetry(throwable)
    }
}