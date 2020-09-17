package com.malwina.weatherstation.citychooser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malwina.weatherstation.databinding.CityItemBinding

class CitiesAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private var cities: List<CityItemRow> = mutableListOf()

    fun setCities(cities: List<CityItemRow>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cityItemBinding = CityItemBinding.inflate(inflater, parent, false)
        return CityViewHolder(cityItemBinding)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city: CityItemRow = cities[position]
        holder.bind(city)
    }
}