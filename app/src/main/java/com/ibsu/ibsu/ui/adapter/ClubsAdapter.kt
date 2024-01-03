package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.domain.model.ClubsItem
import com.ibsu.ibsu.databinding.ClubItemBinding
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.ui.element.student_life.EntertainmentFragmentDirections

class ClubsAdapter(private val context: Context) :
    ListAdapter<com.ibsu.ibsu.domain.model.ClubsItem, ClubsAdapter.ClubViewHolder>(ItemDiffCallback()) {
    inner class ClubViewHolder(private val binding: ClubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                imageBtn.loadFromUrl(source.pictureURL, progressBar, 800)
                imageBtn.setOnClickListener {
                    val action =
                        EntertainmentFragmentDirections.actionEntertainmentFragmentToSingleClubFragment(
                            source
                        )
                    root.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ClubItemBinding.inflate(layoutInflater, parent, false)
        return ClubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.ClubsItem>() {
        override fun areItemsTheSame(oldItem: com.ibsu.ibsu.domain.model.ClubsItem, newItem: com.ibsu.ibsu.domain.model.ClubsItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: com.ibsu.ibsu.domain.model.ClubsItem, newItem: com.ibsu.ibsu.domain.model.ClubsItem): Boolean =
            oldItem == newItem

    }
}
