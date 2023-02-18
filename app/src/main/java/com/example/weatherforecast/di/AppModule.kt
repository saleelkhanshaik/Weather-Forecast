package com.example.weatherforecast.di

import com.example.weatherforecast.network.WeatherNetwork
import com.example.weatherforecast.repository.WeatherRepository
import com.example.weatherforecast.util.WeatherConstants
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun createRetrofitInstance(): WeatherNetwork {
        return Retrofit.Builder().baseUrl(WeatherConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherNetwork::class.java)
    }

    @Provides
    @Singleton
    fun createWeatherNetwork(weatherNetwork: WeatherNetwork):WeatherRepository{
        return WeatherRepository(weatherNetwork)
    }
}