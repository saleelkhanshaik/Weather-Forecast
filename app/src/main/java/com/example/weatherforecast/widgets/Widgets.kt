package com.example.weatherforecast.widgets

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.components.WeatherLoadImage
import com.example.weatherforecast.model.LastOneWeek
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.ui.theme.YellowColor
import com.example.weatherforecast.util.farenheitToCelcius
import com.example.weatherforecast.util.fromDate
import com.example.weatherforecast.util.fromDateTime
import com.example.weatherforecast.util.imageUrlForming

@Composable
fun WeatherDetailedRow(item: LastOneWeek) {
    val minTemp = item.temp.min.farenheitToCelcius()
    val maxTemp = item.temp.max.farenheitToCelcius()
    val weatherIcon = item.weather[0].icon.imageUrlForming()
    val weatherDescription = item.weather[0].description
    val day = item.dt.fromDate().substring(0, 3)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp)
        ),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = day,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            WeatherLoadImage(id = weatherIcon, imageSize = 60.dp)
            Text(
                text = weatherDescription,
                modifier = Modifier
                    .background(
                        color = YellowColor,
                        shape = CircleShape.copy(all = CornerSize(15.dp))
                    )
                    .padding(3.dp),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(maxTemp)
                }
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append(minTemp)
                }
            })
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

@Composable
fun SunsetAndSunriseRow(pSunset: Int?, pSunrise: Int?) {
    val sunset = pSunset?.fromDateTime()
    val sunrise = pSunrise?.fromDateTime()
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise_icon",
                modifier = Modifier.size(24.dp)
            )
            Text(text = sunrise ?: "6:00 AM", style = MaterialTheme.typography.titleMedium)
        }
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = sunset ?: "06:55 PM", style = MaterialTheme.typography.titleMedium)
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset_icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun WeatherHomeScreenTopView(weatherResponse: WeatherResponse?) {
    val currentDayReport = weatherResponse?.list?.get(0)
    val currentDayWeatherReport = currentDayReport?.weather?.get(0)
    val today: String? = currentDayReport?.dt?.fromDate()
    val iconId = currentDayWeatherReport?.icon ?: "10d"
    val weatherMain = currentDayWeatherReport?.main
    val tempDay = currentDayReport?.temp?.day?.farenheitToCelcius()
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
        SunsetAndSunriseRow(currentDayReport?.sunset, currentDayReport?.sunrise)
        Text(text = "This Week", style = MaterialTheme.typography.titleMedium)
        weatherResponse?.list?.let { ThisWeekItem(it) }
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
fun ThisWeekItem(list: List<LastOneWeek>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(size = 14.dp),
        color = Color(0xFFEEF1EF)
    ) {
        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(list) { item ->
                WeatherDetailedRow(item)
            }
        }
    }
}