package com.example.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoapp.data.local.DatabaseModule
import com.example.photoapp.data.api.NetworkModule
import com.example.photoapp.domain.model.PhotoRepository
import com.example.photoapp.ui.theme.PhotoAppTheme
import com.example.photoapp.ui.viewmodel.PexelsViewModel
import com.example.photoapp.ui.screens.PhotosScreen

class MainActivity : ComponentActivity() {
  private val apiKey = "TS1rLcApC1aFa8B3n1b40fPjMcv6GwntZuyvXstHLu1zdD4vdEWjH4BV"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//val database= PhotoDatabase.getInstance(context=this)
    val api = NetworkModule.providePexelsApi(apiKey)
    val dao = DatabaseModule.providePhotoDao(applicationContext)
    val repo = PhotoRepository(api, dao)

    setContent {
      MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {

          val viewModel: PexelsViewModel = viewModel(
            factory = object : androidx.lifecycle.ViewModelProvider.Factory {
              override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return PexelsViewModel(repo) as T
              }
            }
          )
          PhotosScreen(viewModel)
        }
      }
    }
  }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  PhotoAppTheme {

  }
}
