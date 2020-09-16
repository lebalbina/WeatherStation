package com.malwina.weatherstation.citychooser

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.malwina.weatherstation.R
import com.malwina.weatherstation.weatherapi.response.City

class CityViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    val onItemClicked: (city: City) -> Unit
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.city_item, parent, false)) {
    private var cityName: TextView? = null
    private var country: TextView? = null
    private var area: TextView? = null
    private var cityItem: ViewGroup? = null

    init {
        cityName = itemView.findViewById(R.id.cityNameTxt)
        country = itemView.findViewById(R.id.countryTxt)
        area = itemView.findViewById(R.id.areaTxt)
        cityItem = itemView.findViewById(R.id.cityItem)
    }

    fun bind(city: City) {
        cityName?.text = city.name
        country?.text = city.country
        area?.text = city.area

        cityItem?.setOnClickListener {
            onItemClicked(city)
        }
    }
}