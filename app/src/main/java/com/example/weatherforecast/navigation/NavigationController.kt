package com.example.weatherforecast.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecast.screens.home.WeatherHomeScreen
import com.example.weatherforecast.screens.WeatherSplashScreen
import com.example.weatherforecast.screens.home.HomeScreenViewModel
import com.example.weatherforecast.screens.search.SearchScreen

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
        //www.google.com/cityName="Chennai"
        val homeScreenRoute = WeatherScreens.HomeScreen.name
        composable("$homeScreenRoute/{cityName}",
            arguments = listOf(
                navArgument(name = "cityName") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("cityName").let { cityName ->
                Log.i("WeatherSplashScreen saleel 03", "WeatherNavigation: ")
                WeatherHomeScreen(navController,cityName)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}