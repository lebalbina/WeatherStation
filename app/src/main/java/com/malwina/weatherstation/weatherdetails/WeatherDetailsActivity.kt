package com.malwina.weatherstation.weatherdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.malwina.weatherstation.R
import com.malwina.weatherstation.alerter.AlertsProvider
import com.malwina.weatherstation.citychooser.CityChooserActivity
import com.malwina.weatherstation.databinding.WeatherDetailsActivityBinding
import com.malwina.weatherstation.model.City

class WeatherDetailsActivity : AppCompatActivity() {
    private lateinit var binding: WeatherDetailsActivityBinding

    private val viewModel: WeatherDetailsViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            WeatherDetailsViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.weather_details_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val cityId = intent.extras?.get(CityChooserActivity.CHOSEN_CITY) as City
        viewModel.init(cityId.id, cityId.name)

        viewModel.error.observe(this, Observer {
            AlertsProvider.showErrorAlert(this, getString(R.string.error), R.drawable.ic_error_outline_24px)
        })
    }
}