package com.example.photoapp.data.model

import com.google.gson.annotations.SerializedName
import com.example.photoapp.domain.model.Photo

data class PexelsResponse(
  val page: Int,
  val per_page: Int,
  val total_results: Int?,
  val photos: List<Photo>
)
