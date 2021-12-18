package com.gdn.tms.android.alertingsystemhackaton.wrapper

sealed class ResultWrapper<out T> {
  data class Success<out T>(val value: T) : ResultWrapper<T>()
  data class HttpError(val code: Int? = null, val error: String? = null) : ResultWrapper<Nothing>()
  object NetworkError : ResultWrapper<Nothing>()
}