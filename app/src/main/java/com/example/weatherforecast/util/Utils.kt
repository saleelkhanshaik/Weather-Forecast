package com.example.weatherforecast.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.fromDate():String{
    val sdf = SimpleDateFormat("EEEE, MM d", Locale.ROOT)
    val date = Date(this.toLong() * 1000)
    return sdf.format(date)
}

fun Int.fromDateTime():String{
    val sdf = SimpleDateFormat("hh:mm:aa",Locale.ROOT)
    val date = Date(this.toLong() * 1000)
    return sdf.format(date)
}

fun Double.formatDecimals():String{
    return " %.0f".format(this)
}

fun String.imageUrlForming():String{
    return WeatherConstants.BASE_IMAGE_URL.plus(this).plus(".png")
}

//(85°F − 32) × 5/9
fun Double.FarenheitToCelcius():String{
    Log.i("TAG", "FarenheitToCelcius: $this")
    return  ((this - 34) * 5/9).formatDecimals().plus(" ℃")
}