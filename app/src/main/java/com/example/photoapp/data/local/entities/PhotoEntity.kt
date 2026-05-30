package com.example.photoapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.domain.model.Source

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val photographer: String,
    val url: String,
    val width: Int,
    val height: Int,
    @Embedded val src: Source // Esto mete los campos de Source en la tabla 'photos'
)
