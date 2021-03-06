package com.udacity.project4.ui.remainderList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.databinding.ItemRemainderBinding
import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */


interface RemainderAdapterCallback {
    fun itemDelete(remainder: Remainder)
    fun onItemClick(remainder: Remainder)
}

class RemainderAdapter(private val callback: RemainderAdapterCallback) :
    ListAdapter<Remainder, RemainderVH>(diffUtils) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemainderVH {

        return RemainderVH(
            ItemRemainderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )
    }

    override fun onBindViewHolder(holder: RemainderVH, position: Int) {
        holder.onBind(getItem(position))
    }


    companion object {
        val diffUtils = object : DiffUtil.ItemCallback<Remainder>() {
            override fun areItemsTheSame(oldItem: Remainder, newItem: Remainder): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Remainder, newItem: Remainder): Boolean {
                return oldItem == newItem
            }
        }
    }
}


class RemainderVH(
    private val binding: ItemRemainderBinding,
    private val callback: RemainderAdapterCallback
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Remainder) {
        binding.apply {
            remainder = item
            ivDelete.setOnClickListener { callback.itemDelete(item) }
            this.root.setOnClickListener {
                callback.onItemClick(item)
            }
        }
    }
}