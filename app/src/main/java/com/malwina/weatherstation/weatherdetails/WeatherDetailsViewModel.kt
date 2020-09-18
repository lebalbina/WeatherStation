package com.malwina.weatherstation.weatherdetails

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.model.CurrentConditions
import com.malwina.weatherstation.model.Forecast
import com.malwina.weatherstation.model.toDomain
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
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
            buildWeatherDetails(it.first.toDomain(), it.second.toDomain())
        }
    }

    private fun buildWeatherDetails(currentConditions: CurrentConditions, forecast: Forecast)
            : WeatherDetails {
        return WeatherDetails(
            minTemp = forecast.minTemp.toString() + CELSIUS_SIGN,
            maxTemp = forecast.maxTemp.toString() + CELSIUS_SIGN,
            currentTemp = currentConditions.currentTemp.toString() + CELSIUS_SIGN,
            link = context.getString(
                R.string.icon_link,
                getIconNumber(currentConditions.weatherIcon)
            ),
            tempColor = selectTempColor(currentConditions.currentTemp)
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

data class WeatherDetails(
    val minTemp: String,
    val maxTemp: String,
    val currentTemp: String,
    val link: String?,
    val tempColor: Int
)