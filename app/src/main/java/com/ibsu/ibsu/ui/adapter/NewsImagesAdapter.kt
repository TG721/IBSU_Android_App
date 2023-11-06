package com.ibsu.ibsu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.databinding.NewsImageItemBinding
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.ui.element.NewsDescriptionFragmentDirections

class NewsImagesAdapter :
    ListAdapter<String, NewsImagesAdapter.ItemViewHolder>(ItemDiffCallback()) {

    inner class ItemViewHolder(private val binding: NewsImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(position: Int) {

            val source = getItem(absoluteAdapterPosition)
            binding.imageView.loadFromUrl(source, binding.progressBar)

            binding.imageView.setOnClickListener {
                val action =
                    NewsDescriptionFragmentDirections.actionNewsDescriptionFragmentToNewsImageFullScreenFragment(
                        position, currentList.toTypedArray()
                    )
                binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsImageItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }

}