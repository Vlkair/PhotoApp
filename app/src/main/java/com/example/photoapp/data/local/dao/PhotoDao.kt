package com.example.photoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoapp.data.local.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos ORDER BY id ASC")
    fun getAllPhotos(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //reemplazará la info al haber algun conflicto
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()
}
