package com.gdn.tms.android.alertingsystemhackaton

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
const val HOSTNAME = "hostname"

@Singleton class EnvironmentInterceptor @Inject constructor(private val preference: SharedPreferences) :
  Interceptor {

  private var host: String? = null

  @Synchronized fun interceptHost(host: String?) {
    host?.let {
      this.host = it
      preference.edit().putString(HOSTNAME, host).commit()
    }
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    var request = chain.request()
    val host = if (host != null) host else preference.getString(HOSTNAME, null)
    host?.let {
      host.toHttpUrlOrNull()?.let {
        request.url.newBuilder().host(it.host).port(it.port).scheme(it.scheme).build().apply {
          request = request.newBuilder().url(this).build()
        }
      }
    } ?: kotlin.run {
      Log.e("error","error in sending request")
    }
    return chain.proceed(request)
  }
}