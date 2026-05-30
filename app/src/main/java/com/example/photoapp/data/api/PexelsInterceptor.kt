package com.example.photoapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

class PexelsInterceptor(private val apiKey: String) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .addHeader("Authorization", apiKey) // Pexels lee la clave directamente aquí
      .build()
    return chain.proceed(request)
  }
}
