package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ibsu.ibsu.data.remote.model.GamesItem
import com.ibsu.ibsu.databinding.GameItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class GamesAdapter(private val context: Context) :
    ListAdapter<GamesItem, GamesAdapter.GameViewHolder>(ItemDiffCallback()) {
    inner class GameViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                Glide.with(imageBtn)
                    .load(source.pictureURL)
//                    .apply(RequestOptions.placeholderOf(R.drawable.ping_pong))  // Optional: Set a placeholder image
                    .transition(DrawableTransitionOptions.withCrossFade())  // Optional: Add a fade-in animation
                    .into(imageBtn)
            }
            if(context.getCurrentLocale(context).language==georgianLocale)
            binding.titleTV.text = source.nameGe
            else binding.titleTV.text = source.nameEn
//            binding.roomTV.text = source.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GameItemBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<GamesItem>() {
        override fun areItemsTheSame(oldItem: GamesItem, newItem: GamesItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: GamesItem, newItem: GamesItem): Boolean =
            oldItem == newItem

    }
}
