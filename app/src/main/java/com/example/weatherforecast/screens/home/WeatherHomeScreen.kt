package com.example.weatherforecast.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
        ShowData(viewModel = mainViewModel)
    }
}

@Composable
fun ShowData(viewModel: HomeScreenViewModel) {
    val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = viewModel.getWeatherData("Chennai")
    }.value

    Log.i("WeatherHomeScreen", "ShowData: ${weatherData.data}")
    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        Text(text = "Main Screen ${weatherData.data?.city?.country}")
    }

}