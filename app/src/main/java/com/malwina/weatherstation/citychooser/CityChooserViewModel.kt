package com.malwina.weatherstation.citychooser

import android.app.Application
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.alerter.AlertsProvider
import com.malwina.weatherstation.model.City
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
import com.malwina.weatherstation.weatherapi.response.toDomain
import com.malwina.weatherstation.weatherdetails.WeatherDetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CityChooserViewModel(private val context: Application) : AndroidViewModel(context) {
    private val weatherService = WeatherServiceProvider.getService()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val citiesList: MutableLiveData<List<CityItemRow>> = MutableLiveData()
    val validatedInput: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Unit> = MutableLiveData()

    fun getMatchingCities(cityName: String) {
        compositeDisposable.add(
            weatherService.getLocations(
                city = cityName,
                apiKey = context.getString(R.string.api_key)
            ).map { it.toDomain().toCityItemRows() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = { citiesList.postValue(it) },
                    onError = { error.postValue(Unit) })
        )
    }

    fun validateInput(editable: Editable) {
        val regex = "[a-zA-Z]+[a-z]+([\\s-][a-zA-Z]+[a-z]?)*".toRegex()
        validatedInput.postValue(editable.matches(regex))
    }
}

fun List<City>.toCityItemRows(): List<CityItemRow> {
    return this.map {
        CityItemRow(
            onClick = { view: View ->
                val intent = Intent(view.context, WeatherDetailsActivity::class.java)
                intent.putExtra(CityChooserActivity.CHOSEN_CITY, it)
                view.context.startActivity(intent)
            },
            nameAndLocation = it.name + ", " + it.area,
            country = it.country
        )
    }
}

data class CityItemRow(
    val onClick: (view: View) -> Unit,
    val nameAndLocation: String,
    val country: String
)