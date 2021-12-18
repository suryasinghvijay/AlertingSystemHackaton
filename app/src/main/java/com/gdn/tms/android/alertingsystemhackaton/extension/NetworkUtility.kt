package com.gdn.tms.android.alertingsystemhackaton.extension

import android.util.Log
import com.gdn.tms.android.alertingsystemhackaton.wrapper.ResultWrapper
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(
  dispatcher: CoroutineDispatcher, apiCall: suspend () -> T
): ResultWrapper<T> {
  return withContext(dispatcher) {
    try {
      ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
      Log.e("exception in safe Api ",throwable.toString())
      when (throwable) {
        is IOException -> ResultWrapper.NetworkError
        is HttpException -> {
          val code = throwable.code()
          val errorResponse = throwable.message() ?: "something went wrong please try again later"
          ResultWrapper.HttpError(code, errorResponse)
        }
        else -> {
          ResultWrapper.HttpError(null, throwable.message)
        }
      }
    }
  }
}