package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.network.WeatherNetwork
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherNetwork: WeatherNetwork) {
    suspend fun getWeatherDetails(cityName:String):DataOrException<WeatherResponse,Boolean,Exception>{
            val response = try{
                weatherNetwork.getWeatherDetailsForEnteredCity(cityName)
            }catch (e:Exception){
                return DataOrException(exception = e)
            }
        Log.i("WeatherRepository", "getWeatherDetails: $response")
        return DataOrException(data = response)
    }


}