package com.example.weatherforecast.screens


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController = rememberNavController()) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.8F, animationSpec = tween(
            durationMillis = 1000,
            easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            },
            delayMillis = 100
        ))
        delay(1500L)
        navController.navigate(WeatherScreens.HomeScreen.name){
            popUpTo(WeatherScreens.SplashScreen.name){
                inclusive = true
            }
        }
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun pic",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(95.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Find the Sun?",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.LightGray
            )
        }
    }
}
