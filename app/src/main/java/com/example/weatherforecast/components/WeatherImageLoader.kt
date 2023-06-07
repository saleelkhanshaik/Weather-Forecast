package com.example.weatherforecast.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun WeatherLoadImage(id:String,imageSize :Dp = 80.dp){
    Log.i("WeatherLoadImage", "WeatherLoadImage: $id")
    val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current).data(id).size(Size.ORIGINAL).build())
    Image(painter = painter, contentDescription = "weatherState",
        modifier = Modifier.size(80.dp))

}