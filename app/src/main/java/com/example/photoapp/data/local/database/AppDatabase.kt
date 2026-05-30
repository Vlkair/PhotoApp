package com.example.photoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.photoapp.data.local.dao.PhotoDao
import com.example.photoapp.data.local.entities.PhotoEntity

// 1. Define las entidades y la versión
@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // 2. Define el acceso al DAO
    abstract fun photoDao(): PhotoDao

    // Aquí podrías añadir un companion object para el Singleton
    // si no usas inyección de dependencias (Hilt/Koin)
}