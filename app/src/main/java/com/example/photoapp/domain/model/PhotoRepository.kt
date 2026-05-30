package com.example.photoapp.domain.model

import com.example.photoapp.data.api.PexelsApi
import com.example.photoapp.data.local.dao.PhotoDao
import com.example.photoapp.data.local.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotoRepository(
    private val api: PexelsApi,
    private val dao: PhotoDao
) {
    // 1. El Flow que la UI observará (Offline First)
    // Automáticamente emite nuevos valores cuando la base de datos cambia
    val photos: Flow<List<Photo>> = dao.getAllPhotos().map { entities ->
        entities.map { it.toDomain() }
    }

    // 2. Función para refrescar los datos desde la API
    suspend fun refreshPhotos() {
        try {
            val response = api.getCurated(page = 1, perPage = 30)
            if (response.isSuccessful) {
                val networkPhotos = response.body()?.photos ?: emptyList()
                val entities = networkPhotos.map { it.toEntity() }

                // Actualizamos la base de datos de forma atómica
                dao.deleteAllPhotos()
                dao.insertPhotos(entities)
            }
        } catch (e: Exception) {
            // Aquí se podría manejar el error de red (ej. logging)
            // La UI seguirá mostrando lo que haya en Room
        }
    }
}

// Mappers para convertir entre capas
fun PhotoEntity.toDomain() = Photo(
    id = id,
    width = width,
    height = height,
    url = url,
    photographer = photographer,
    src = src
)

fun Photo.toEntity() = PhotoEntity(
    id = id,
    photographer = photographer,
    url = url,
    width = width,
    height = height,
    src = src
)
