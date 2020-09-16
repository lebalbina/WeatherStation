package com.malwina.weatherstation.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl == null) return
    Glide
        .with(view)
        .load(imageUrl)
        .centerInside()
        .into(view)
}