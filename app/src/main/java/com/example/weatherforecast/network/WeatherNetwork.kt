package com.example.weatherforecast.network


import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.util.WeatherConstants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

//https://api.openweathermap.org/ = BASE_URL
// data/2.5/forecast/daily = GET value
// ?q = Query
// =nellore
// &appid=ed60fcfbd110ee65c7150605ea8aceea
// &units=imperial
@Singleton
interface WeatherNetwork {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherDetailsForEnteredCity(
        @Query("q") cityName: String,
        @Query("appid") appid: String = WeatherConstants.API_KEY,
        @Query("units") units: String = "imperial"
    ): WeatherResponse

}