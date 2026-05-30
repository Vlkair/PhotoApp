package com.example.photoapp.data.repository

import com.example.photoapp.data.model.PexelsResponse
import com.example.photoapp.data.api.PexelsApi

class PexelsRepository(private val api: PexelsApi) {
  suspend fun fetchCurated(page: Int = 2, perPage: Int = 40): Result<PexelsResponse> {
    return try {
      val resp = api.getCurated(page = page, perPage = perPage)
      if (resp.isSuccessful) {
        val body = resp.body()
        if (body != null) Result.success(body) else Result.failure(Exception("Empty body"))
      } else {
        Result.failure(Exception("HTTP ${resp.code()} ${resp.message()}"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
// suspend fun refreshCurated(page: Int = 1, perPage: Int = 40)){
// try{
//  val resp= api.getCurated(page = page, perPage = perPage)
//  if(resp.isSuccessful){
//    val body = resp.body()
//    if(body != null){
//      dao.deleteAll()
//      dao.insertAll(photos=body.photos.map{it.toEntity()})
//    }
//  }
// }catch(_:exception e){}
// }