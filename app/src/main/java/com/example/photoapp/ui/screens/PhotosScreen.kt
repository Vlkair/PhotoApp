package com.example.photoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.photoapp.domain.model.Photo
import com.example.photoapp.ui.viewmodel.PexelsViewModel
@Composable
fun PhotosScreen(viewModel: PexelsViewModel) {

  val photos by viewModel.photos.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadCurated()
  }

  LazyColumn( modifier = Modifier
  .fillMaxSize()
  .padding(8.dp),
  verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    if (photos.isEmpty()) {
      item {
        Text("Cargando fotos...", style = MaterialTheme.typography.bodyLarge)
      }
    } else {
      items(photos, key = { it.id }) { photo ->
        PhotoItem(photo)
      }
    }
  }
}

@Composable
fun PhotoItem(photo: Photo) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .height(220.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Image(
        painter = rememberAsyncImagePainter(photo.src?.large),
        contentDescription = photo.photographer,
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp),
        contentScale = ContentScale.Crop
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Por: ${photo.photographer}",
        style = MaterialTheme.typography.bodyMedium
      )
      Text(
        text = "ID: ${photo.id}",
        style = MaterialTheme.typography.bodySmall
      )
    }
  }
}
