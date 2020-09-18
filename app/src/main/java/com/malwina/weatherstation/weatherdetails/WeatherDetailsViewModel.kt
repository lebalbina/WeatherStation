package com.malwina.weatherstation.weatherdetails

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.model.WeatherDetails
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
import com.malwina.weatherstation.weatherapi.response.CurrentConditionsResponse
import com.malwina.weatherstation.weatherapi.response.ForecastResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class WeatherDetailsViewModel(private val context: Application) : AndroidViewModel(context) {
    private val weatherService = WeatherServiceProvider.getService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val weatherDetails: MutableLiveData<WeatherDetails> = MutableLiveData()
    val error: MutableLiveData<Unit> = MutableLiveData()
    val cityName: MutableLiveData<String> = MutableLiveData()
    val loader: MutableLiveData<Boolean> = MutableLiveData()

    fun init(cityId: String, cityName: String) {
        this.cityName.postValue(cityName)
        loader.postValue(true)
        compositeDisposable.add(
            getWeatherDetails(cityId, context.getString(R.string.api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = {
                        loader.postValue(false)
                        weatherDetails.postValue(it)
                    },
                    onError = {
                        loader.postValue(true)
                        error.postValue(Unit)
                    }
                ))
    }

    private fun getWeatherDetails(cityId: String, apiKey: String): Single<WeatherDetails> {
        return Singles.zip(
            weatherService.getCurrentWeather(cityId, apiKey),
            weatherService.getForecast(cityId, apiKey)
        ).map {
            buildWeatherDetails(it.first.last(), it.second)
        }
    }

    private fun buildWeatherDetails(
        currentConditionsResponse: CurrentConditionsResponse,
        forecastResponse: ForecastResponse
    ): WeatherDetails {
        return WeatherDetails(
            minTemp = forecastResponse.dailyForecasts.last().temperature.minimum.value.toString() + CELSIUS_SIGN,
            maxTemp = forecastResponse.dailyForecasts.last().temperature.maximum.value.toString() + CELSIUS_SIGN,
            currentTemp = currentConditionsResponse.temperature.metric.value.toString() + CELSIUS_SIGN,
            link = context.getString(
                R.string.icon_link,
                getIconNumber(currentConditionsResponse.weatherIcon)
            ),
            tempColor = selectTempColor(currentConditionsResponse.temperature.metric.value),
            weatherText = currentConditionsResponse.weatherText,
            realFeel = currentConditionsResponse.realFeelTemperature.metric.value.toString() + CELSIUS_SIGN,
            humidity = currentConditionsResponse.relativeHumidity,
            hasPrecipitation = currentConditionsResponse.hasPrecipitation,
            precipitationType = currentConditionsResponse.precipitationType
        )
    }

    private fun getIconNumber(iconNo: Int?): String {
        if (iconNo == null) return ""
        return if (iconNo < 10) "0$iconNo" else iconNo.toString()
    }

    private fun selectTempColor(temp: Double?): Int {
        if (temp == null) return 0
        return when {
            temp > 20 -> Color.RED
            temp in 10.0..20.0 -> Color.BLACK
            else -> Color.BLUE
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val CELSIUS_SIGN = " \u2103"
    }
}