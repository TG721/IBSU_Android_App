package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ibsu.ibsu.data.remote.model.ClubsItem
import com.ibsu.ibsu.databinding.ClubItemBinding
import com.ibsu.ibsu.ui.element.EntertainmentFragmentDirections

class ClubsAdapter(private val context: Context) :
    ListAdapter<ClubsItem, ClubsAdapter.ClubViewHolder>(ItemDiffCallback()) {
    inner class ClubViewHolder(private val binding: ClubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                Glide.with(imageBtn)
                    .load(source.pictureURL)
//                    .apply(RequestOptions.placeholderOf(R.drawable.music_club))  // Optional: Set a placeholder image
                    .transition(DrawableTransitionOptions.withCrossFade())  // Optional: Add a fade-in animation
                    .into(imageBtn)
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

    private class ItemDiffCallback : DiffUtil.ItemCallback<ClubsItem>() {
        override fun areItemsTheSame(oldItem: ClubsItem, newItem: ClubsItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ClubsItem, newItem: ClubsItem): Boolean =
            oldItem == newItem

    }
}
