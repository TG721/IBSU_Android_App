package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.domain.model.ExchangeUniversityItem
import com.ibsu.ibsu.databinding.ExchangeUniversityItemBinding
import com.ibsu.ibsu.extensions.loadFromUrl

class ExchangeUniversityItemAdapter(private val context: Context) :
    ListAdapter<com.ibsu.ibsu.domain.model.ExchangeUniversityItem, ExchangeUniversityItemAdapter.ViewHolder>(ItemDiffCallback()) {
    inner class ViewHolder(private val binding: ExchangeUniversityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                universityNameTV.text = source.name
                deadline.text = "Deadline:" + " " + source.deadline
                country.text = "Country:" + " " + source.county
                moreDetails.text = source.Details
                if (source.image != null) {
                    imageView.loadFromUrl(source.image, progressBar, 800)

                } else imageView.visibility = View.GONE


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ExchangeUniversityItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.ExchangeUniversityItem>() {
        override fun areItemsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.ExchangeUniversityItem,
            newItem: com.ibsu.ibsu.domain.model.ExchangeUniversityItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.ExchangeUniversityItem,
            newItem: com.ibsu.ibsu.domain.model.ExchangeUniversityItem,
        ): Boolean =
            oldItem == newItem

    }
}
