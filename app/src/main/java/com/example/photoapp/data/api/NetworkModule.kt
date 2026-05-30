package com.example.photoapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.photoapp.data.api.Constants

object NetworkModule {

  fun providePexelsApi(apiKey: String): PexelsApi {

    val logging = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
      .addInterceptor(PexelsInterceptor(apiKey))
      .addInterceptor(logging)
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    return retrofit.create(PexelsApi::class.java)
  }
}
