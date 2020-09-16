package com.malwina.weatherstation.citychooser

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
import com.malwina.weatherstation.weatherapi.response.City
import com.malwina.weatherstation.weatherapi.response.toDomain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CityChooserViewModel(private val context: Application) : AndroidViewModel(context) {
    private val weatherService = WeatherServiceProvider().getService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val citiesList: MutableLiveData<List<City>> = MutableLiveData()
    val validatedInput: MutableLiveData<Boolean> = MutableLiveData()

    fun getMatchingCities(cityName: String) {
        compositeDisposable.add(
            weatherService.getLocations(
                city = cityName,
                apiKey = context.getString(R.string.api_key)
            ).map { it.toDomain() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = { citiesList.postValue(it) },
                    onError = {
                        Log.d("balb", "error + ${it.localizedMessage}")
                    })
        )
    }

    fun validateInput(editable: Editable) {
        val regex = "[a-zA-Z]+[a-z]+([\\s-][a-zA-Z]+[a-z]?)*".toRegex()
        validatedInput.postValue(editable.matches(regex))
    }
}