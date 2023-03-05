package com.example.weatherforecast.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.screens.home.WeatherHomeScreen
import com.example.weatherforecast.screens.WeatherSplashScreen
import com.example.weatherforecast.screens.home.HomeScreenViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            Log.i("WeatherSplashScreen saleel 01", "WeatherNavigation: ")
            WeatherSplashScreen(navController)
        }
        composable(WeatherScreens.HomeScreen.name) {
            Log.i("WeatherSplashScreen saleel 03", "WeatherNavigation: ")
            WeatherHomeScreen(navController)
        }

    }
}