package com.malwina.weatherstation.citychooser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malwina.weatherstation.weatherapi.response.City

class CitiesAdapter(private val onItemClick: (city: City) -> Unit) :
    RecyclerView.Adapter<CityViewHolder>() {

    private var cities: List<City> = mutableListOf()

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CityViewHolder(inflater, parent, onItemClick)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city: City = cities[position]
        holder.bind(city)
    }
}