package com.gdn.tms.android.alertingsystemhackaton.di.module

import android.content.Context
import android.content.SharedPreferences
import com.gdn.tms.android.alertingsystemhackaton.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SHARED_PREFERENCES_NAME = "AlertingSystem"

@Module @InstallIn(SingletonComponent::class) object AppModule {

  @Provides @Singleton fun provideSharedPreference(
    @ApplicationContext context: Context
  ): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
  }

  @Provides @Singleton fun provideLoggerInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides @Singleton fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient = OkHttpClient().newBuilder().apply {
    addInterceptor(httpLoggingInterceptor)
  }.build()

  @Singleton @Provides fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl("http://192.168.0.100:9191/")
      .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()

  @Provides fun provideGson(): Gson = GsonBuilder().create()

  @Provides @Singleton fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
}