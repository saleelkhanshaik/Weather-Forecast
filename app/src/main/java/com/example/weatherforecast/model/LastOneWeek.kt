package com.example.weatherforecast.model

data class LastOneWeek(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feelsLike: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<Weather>
)