package com.example.photoapp.ui.screens

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.photoapp.domain.model.Photo
import com.example.photoapp.ui.viewmodel.PexelsViewModel

@Composable
fun PhotosScreen(viewModel: PexelsViewModel) {

  val photos by viewModel.photos.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadCurated()
  }

  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
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

    // ¡La pandilla de animales!
    FloatingFlame()
    FloatingFrog()
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

@Composable
fun FloatingFlame() {
  val infiniteTransition = rememberInfiniteTransition(label = "llamaWalk")

  val offsetX by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 100f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "xPos"
  )

  val offsetY by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = -15f,
    animationSpec = infiniteRepeatable(
      animation = tween(250),
      repeatMode = RepeatMode.Reverse
    ),
    label = "yPos"
  )

  Box(
    modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
    contentAlignment = Alignment.BottomStart
  ) {
    Text(
      text = "🦙",
      fontSize = 50.sp,
      modifier = Modifier
        .graphicsLayer(
          translationX = offsetX * 3,
          translationY = offsetY
        )
    )
  }
}

@Composable
fun FloatingFrog() {
  val infiniteTransition = rememberInfiniteTransition(label = "frogJump")

  // Movimiento horizontal (va a un ritmo diferente que la llama)
  val offsetX by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 110f,
    animationSpec = infiniteRepeatable(
      animation = tween(5000, easing = LinearOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "frogX"
  )

  // Salto de la rana (más alto y enérgico)
  val offsetY by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = -80f,
    animationSpec = infiniteRepeatable(
      animation = tween(400),
      repeatMode = RepeatMode.Reverse
    ),
    label = "frogY"
  )

  Box(
    modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, start = 50.dp),
    contentAlignment = Alignment.BottomStart
  ) {
    Text(
      text = "🐸",
      fontSize = 35.sp,
      modifier = Modifier
        .graphicsLayer(
          translationX = offsetX * 2.5f,
          translationY = offsetY
        )
    )
  }
}
