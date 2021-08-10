package com.udacity.project4.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 18,July,2021
 */


@BindingAdapter("adapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: ListAdapter<*, *>) {
    recyclerView.adapter = adapter
}

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<Remainder>?) {
    (recyclerView.adapter as ListAdapter<*, *>).submitList(items as List<Nothing>?)
}


@BindingAdapter("latlng")
fun bindLatLng(textView: TextView, remainder: Remainder?) {
    remainder?.let {
        textView.text = "${remainder.latitude}, ${remainder.longitude}"
    }
}