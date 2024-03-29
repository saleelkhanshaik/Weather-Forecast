package com.example.weatherforecast.screens


import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(navController: NavController) {
    Log.i("WeatherSplashScreen saleel", "WeatherSplashScreen: ")
    val defaultCityName = "Chennai"
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.7F,
            animationSpec = tween(
                durationMillis = 800,
            easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            }
            )
        )
        delay(1500L)
//        navController.popBackStack()
        navController.navigate(WeatherScreens.HomeScreen.name+"/$defaultCityName")
        {
            popUpTo(WeatherScreens.SplashScreen.name){
                inclusive = true
            }
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(330.dp).
            scale(scale.value),
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Text() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(330.dp),
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
}
