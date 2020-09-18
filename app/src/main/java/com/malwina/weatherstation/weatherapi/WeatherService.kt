package com.malwina.weatherstation.weatherapi

import com.malwina.weatherstation.weatherapi.response.CurrentConditionsResponse
import com.malwina.weatherstation.weatherapi.response.ForecastResponse
import com.malwina.weatherstation.weatherapi.response.Location
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("currentconditions/v1/{locationKey}")
    fun getCurrentWeather(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String,
        @Query("details") details : Boolean = true
    ): Single<List<CurrentConditionsResponse>>

    @GET("locations/v1/cities/autocomplete")
    fun getLocations(
        @Query("apikey") apiKey: String,
        @Query("q") city: String
    ): Single<List<Location>>

    @GET("forecasts/v1/daily/1day/{locationKey}")
    fun getForecast(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String,
        @Query("metric") metric: Boolean = true
    ) : Single<ForecastResponse>
}