package com.example.photoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import com.example.photoapp.domain.model.Photo
import com.example.photoapp.domain.model.PhotoRepository

class PexelsViewModel(private val repo: PhotoRepository) : ViewModel() {

    // Observamos el Flow de fotos directamente del repositorio (Room)
    // Esto es Offline-First: la UI se suscribe a los cambios de la base de datos
    val photos: StateFlow<List<Photo>> = repo.photos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Al iniciar, refrescamos los datos desde internet en segundo plano
        loadCurated()
    }

    fun loadCurated() {
        viewModelScope.launch {
            repo.refreshPhotos()
        }
    }
}
