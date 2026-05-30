package com.example.photoapp.domain.model

data class Photo(
  val id: Int,
  val width: Int,
  val height: Int,
  val url: String,
  val photographer: String,
  val src: Source
)

