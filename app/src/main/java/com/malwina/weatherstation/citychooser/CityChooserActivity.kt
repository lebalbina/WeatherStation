package com.malwina.weatherstation.citychooser

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.malwina.weatherstation.R
import com.malwina.weatherstation.alerter.AlertsProvider
import com.malwina.weatherstation.databinding.CityChooserActivityBinding
import kotlinx.android.synthetic.main.city_chooser_activity.*

class CityChooserActivity : AppCompatActivity() {

    private val viewModel: CityChooserViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            CityChooserViewModel::class.java
        )
    }

    private val citiesAdapter: CitiesAdapter = CitiesAdapter()

    private lateinit var binding: CityChooserActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_chooser_activity)
        binding = DataBindingUtil.setContentView(this, R.layout.city_chooser_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.citiesRecyclerView.apply {
            this.adapter = citiesAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        setSearchActionForCityEdt()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.citiesList.observe(this, Observer {
            citiesAdapter.setCities(it)
        })
        viewModel.error.observe(this, Observer {
            AlertsProvider.showErrorAlert(
                this,
                getString(R.string.error),
                R.drawable.ic_error_outline_24px
            )
        })
        viewModel.searchRecords.observe(this, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                R.layout.dropdown_item,
                it
            )
            binding.cityEdt.setAdapter(adapter)
            binding.cityEdt.dropDownVerticalOffset = 12
        })
    }

    private fun setSearchActionForCityEdt() {
        cityEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getMatchingCities(cityEdt.text.toString())
                viewModel.saveSearch(cityEdt.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    companion object {
        const val CHOSEN_CITY = "chosen_city"
    }
}