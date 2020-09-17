package com.malwina.weatherstation.weatherdetails

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.model.CurrentConditions
import com.malwina.weatherstation.model.Forecast
import com.malwina.weatherstation.model.toDomain
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class WeatherDetailsViewModel(private val context: Application) : AndroidViewModel(context) {
    private val weatherService = WeatherServiceProvider.getService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val currentConditions: MutableLiveData<CurrentConditions> = MutableLiveData()
    val forecast: MutableLiveData<Forecast> = MutableLiveData()
    val color: MutableLiveData<Int> = MutableLiveData()
    val cityName: MutableLiveData<String> = MutableLiveData()
    val currentTemp: MutableLiveData<String> = MutableLiveData()
    val link: MutableLiveData<String> = MutableLiveData()

    fun init(cityId: String, cityName: String) {
        this.cityName.postValue(cityName)
        getCurrentWeather(cityId)
        getForecast(cityId)
    }

    private fun getForecast(cityId: String) {
        compositeDisposable.add(
            weatherService.getForecast(
                locationKey = cityId,
                apiKey = context.getString(R.string.api_key)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .map { it.toDomain() }
                .subscribeBy(
                    onSuccess = {
                        forecast.postValue(it)
                    },
                    onError = {
                        Log.d("balb", "byl blad forecast ${it.localizedMessage}")
                    }
                )
        )
    }

    private fun getCurrentWeather(cityId: String) {
        compositeDisposable.add(
            weatherService.getCurrentWeather(
                locationKey = cityId,
                apiKey = context.getString(R.string.api_key)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .map { it.toDomain() }
                .subscribeBy(
                    onSuccess = {
                        currentConditions.postValue(it)
                        currentTemp.postValue(it.currentTemp.toString() + " \u2103")
                        if (it.currentTemp != null)
                            color.postValue(selectTempColor(it.currentTemp))

                        if (it.weatherIcon != null) {
                            link.postValue(context.resources.getString(R.string.icon_link, getIconNumber(it.weatherIcon)))
                        }
                    },
                    onError = {
                        Log.d("balb", "byl blad current ${it.localizedMessage}")
                    })
        )
    }

    private fun getIconNumber(iconNo: Int): String {
        return if (iconNo < 10) "0$iconNo" else iconNo.toString()
    }

    private fun selectTempColor(temp: Double): Int {
        return when {
            temp > 20 -> {
                Color.RED
            }
            temp in 10.0..20.0 -> {
                Color.BLACK
            }
            else -> {
                Color.BLUE
            }
        }
    }
}