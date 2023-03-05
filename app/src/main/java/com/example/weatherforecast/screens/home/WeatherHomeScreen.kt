package com.example.weatherforecast.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.components.WeatherAppBar
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.WeatherResponse

@Composable
fun WeatherHomeScreen(
    navController: NavController,
    mainViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData("Chennai")
        }.value

        Log.i("WeatherHomeScreen", "ShowData: ${weatherData.data}")
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            weatherData.data?.let {
                MainScaffold(weatherResponse = it, navController = navController)
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    weatherResponse: WeatherResponse, navController: NavController
) {
    Scaffold(modifier = modifier,
        topBar = {
            WeatherAppBar(
                title = weatherResponse.city.name.plus(", ").plus(weatherResponse.city.country),
                navController = navController,
                isMainScree = true,
                elevation = 4.dp,
                navigationIcon = null,
                onAddActionClicked = {},
                onButtonClicked = {}
            )
        }) {
        MainContent(weatherResponse)
    }

}

@Composable
fun MainContent(weatherResponse: WeatherResponse) {
    Log.d("MainContent", "MainContent: ${weatherResponse.city.name}")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = weatherResponse.city.name)
    }

}
