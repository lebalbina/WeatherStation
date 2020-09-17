package com.malwina.weatherstation.citychooser

import androidx.recyclerview.widget.RecyclerView
import com.malwina.weatherstation.databinding.CityItemBinding

class CityViewHolder(
    private val binding: CityItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityItemRow) {
        binding.city = city
        binding.executePendingBindings()
    }
}