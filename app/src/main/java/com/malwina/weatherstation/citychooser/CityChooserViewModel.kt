package com.malwina.weatherstation.citychooser

import android.app.Application
import android.content.Intent
import android.text.Editable
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.malwina.weatherstation.R
import com.malwina.weatherstation.database.AppDatabase
import com.malwina.weatherstation.database.recentsearch.RecentSearchData
import com.malwina.weatherstation.model.City
import com.malwina.weatherstation.weatherapi.WeatherServiceProvider
import com.malwina.weatherstation.weatherapi.response.toDomain
import com.malwina.weatherstation.weatherdetails.WeatherDetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CityChooserViewModel(private val context: Application) : AndroidViewModel(context) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val appDatabase: AppDatabase = AppDatabase.create(context)

    val citiesList: MutableLiveData<List<CityItemRow>> = MutableLiveData()
    val validatedInput: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Unit> = MutableLiveData()
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val closeSearch: MutableLiveData<Boolean> = MutableLiveData()
    val searchRecords: MutableLiveData<List<String>> = MutableLiveData()

    init {
        validatedInput.postValue(true)
        subscribeSearchRecords()
    }

    private fun subscribeSearchRecords() {
        compositeDisposable.add(
            appDatabase.recentSearchDao()
                .getRecentSearches()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .map { records -> records.map { it.cityName } }
                .subscribeBy {
                    searchRecords.postValue(it)
                })
    }

    fun getMatchingCities(cityName: String) {
        if (validatedInput.value != null && validatedInput.value == false)
            return

        loader.postValue(true)

        compositeDisposable.add(
            WeatherServiceProvider.weatherService.getLocations(
                city = cityName,
                apiKey = context.getString(R.string.api_key)
            ).map { it.toDomain().toCityItemRows() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = {
                        loader.postValue(false)
                        citiesList.postValue(it)
                    },
                    onError = {
                        loader.postValue(false)
                        error.postValue(Unit)
                    })
        )
    }

    fun saveSearch(cityName: String) {
        compositeDisposable.add(
            appDatabase.recentSearchDao()
                .insertRecentSearch(RecentSearchData(cityName))
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .subscribe()
        )
    }

    fun afterTextChanged(editable: Editable) {
        val regex = "[a-zA-Z]+[a-z]+([\\s-][a-zA-Z]+[a-z]?)*".toRegex()
        validatedInput.postValue(editable.matches(regex) || editable.isEmpty())
        closeSearch.postValue(editable.isNotEmpty())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
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