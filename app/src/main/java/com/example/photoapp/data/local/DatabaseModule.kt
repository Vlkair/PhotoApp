package com.example.photoapp.data.local

import android.content.Context
import androidx.room.Room
import com.example.photoapp.data.local.dao.PhotoDao
import com.example.photoapp.data.local.database.AppDatabase

object DatabaseModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        // Si la instancia ya existe, la devuelve. Si no, la crea.
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "photo_database" // Nombre del archivo de la base de datos
            )
                .fallbackToDestructiveMigration() // Borra y recrea la DB si hay cambios de versión (útil en desarrollo)
                .build()

            INSTANCE = instance
            instance
        }
    }

    fun providePhotoDao(context: Context): PhotoDao {
        return provideDatabase(context).photoDao()
    }
}