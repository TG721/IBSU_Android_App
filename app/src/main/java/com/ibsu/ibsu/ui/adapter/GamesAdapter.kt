package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.databinding.GameItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class GamesAdapter :
    ListAdapter<com.ibsu.ibsu.domain.model.GamesItem, GamesAdapter.GameViewHolder>(ItemDiffCallback()) {
    inner class GameViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val context = binding.root.context
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                imageBtn.loadFromUrl(source.pictureURL, size = 800)
            }
            if (context.getCurrentLocale().language == georgianLocale)
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

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.GamesItem>() {
        override fun areItemsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.GamesItem,
            newItem: com.ibsu.ibsu.domain.model.GamesItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.GamesItem,
            newItem: com.ibsu.ibsu.domain.model.GamesItem,
        ): Boolean =
            oldItem == newItem

    }
}
