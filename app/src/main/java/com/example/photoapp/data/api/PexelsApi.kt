package com.example.photoapp.data.api

import com.example.photoapp.data.model.PexelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
  @GET(Constants.PHOTOS_PATH)
  suspend fun getCurated(
    @Query("page") page: Int = 1,
    @Query("per_page") perPage: Int = 40
  ): Response<PexelsResponse>
}
