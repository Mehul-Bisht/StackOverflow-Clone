package com.example.stackoverflowclone.presentation.filterScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflowclone.databinding.ItemFilterBinding

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class FilterAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var items: List<String>? = null

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val button: Button = binding.filterButton

        fun bind(item: String?) {
            button.text = item ?: ""

            button.setOnClickListener {
                item?.let {
                    onClick(it)
                }
            }
        }
    }

    fun submitList(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {

        val binding = ItemFilterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = items?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}