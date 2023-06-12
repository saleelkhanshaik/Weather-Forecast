package com.example.weatherforecast.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.R
import com.example.weatherforecast.components.WeatherAppBar
import com.example.weatherforecast.components.WeatherLoadImage
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.LastOneWeek
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.ui.theme.YellowColor
import com.example.weatherforecast.util.farenheitToCelcius
import com.example.weatherforecast.util.fromDate
import com.example.weatherforecast.util.fromDateTime
import com.example.weatherforecast.util.imageUrlForming
import com.example.weatherforecast.widgets.HumidityWindPressureRow
import com.example.weatherforecast.widgets.SunsetAndSunriseRow
import com.example.weatherforecast.widgets.WeatherDetailedRow
import com.example.weatherforecast.widgets.WeatherHomeScreenTopView

@Composable
fun WeatherHomeScreen(
    navController: NavController,
    cityName: String?,
    mainViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
) {
    Log.d("WeatherHomeScreen", "WeatherHomeScreen: $cityName")
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
            value = mainViewModel.getWeatherData(cityName?:"Nellore")
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
                onAddActionClicked = {
                                     navController.navigate(WeatherScreens.SearchScreen.name)
                },
                onButtonClicked = {}
            )
        }) { paddingValue ->
        MainContent(Modifier.padding(paddingValue), weatherResponse)
    }

}

@Composable
fun MainContent(modifier: Modifier = Modifier, weatherResponse: WeatherResponse) {
    Log.d("MainContent", "MainContent: ${weatherResponse.city.name}")
    Column(modifier = modifier) {
        WeatherHomeScreenTopView(weatherResponse)
    }
}
