package com.example.weatherforecast.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
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
import com.example.weatherforecast.ui.theme.YellowColor
import com.example.weatherforecast.util.FarenheitToCelcius
import com.example.weatherforecast.util.formatDecimals
import com.example.weatherforecast.util.fromDate
import com.example.weatherforecast.util.fromDateTime
import com.example.weatherforecast.util.imageUrlForming

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
            value = mainViewModel.getWeatherData("Nellore")
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

@Composable
fun WeatherHomeScreenTopView(weatherResponse: WeatherResponse?) {
    val currentDayReport = weatherResponse?.list?.get(0)
    val currentDayWeatherReport = currentDayReport?.weather?.get(0)
    val today: String? = currentDayReport?.dt?.fromDate()
    val iconId = currentDayWeatherReport?.icon ?: "10d"
    val weatherMain = currentDayWeatherReport?.main
    val tempDay = currentDayReport?.temp?.day?.FarenheitToCelcius()
    val weatherStateImageURL = iconId.imageUrlForming()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = today ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp)
        )
        SunCircleShape(weatherStateImageURL, weatherMain, tempDay)
        HumidityWindPressureRow(weather = currentDayReport)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        SunsetAndSunriseRow(currentDayReport?.sunset,currentDayReport?.sunrise)
    }
}

@Composable
fun SunsetAndSunriseRow(pSunset:Int?,pSunrise:Int?){
    val sunset = pSunset?.fromDateTime()
    val sunrise = pSunrise?.fromDateTime()
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise_icon",
                modifier = Modifier.size(24.dp))
            Text(text = sunrise?:"6:00 AM", style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = sunset?:"06:55 PM", style = MaterialTheme.typography.titleMedium)
            Image(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset_icon",
                modifier = Modifier.size(24.dp))
        }
    }
}
@Composable
fun SunCircleShape(weatherStateImageURL: String, weatherMain: String?, tempDay: String?) {
    Surface(
        modifier = Modifier.size(175.dp),
        shape = CircleShape,
        color = YellowColor
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherLoadImage(weatherStateImageURL)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = tempDay ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherMain ?: "",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: LastOneWeek? = null) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity_icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 2.dp)
            )
            Text(text = "${weather?.humidity}%", style = MaterialTheme.typography.titleSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure_icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 2.dp)
            )
            Text(text = "${weather?.pressure} psi", style = MaterialTheme.typography.titleSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind), contentDescription = "wind_icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 2.dp)
            )
            Text(text = "${weather?.speed} mph", style = MaterialTheme.typography.titleSmall)
        }
    }
}