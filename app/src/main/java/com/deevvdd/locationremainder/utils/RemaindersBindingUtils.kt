package com.deevvdd.locationremainder.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.locationremainder.domain.model.Remainder

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