package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.databinding.NewsItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.ui.element.student_life.EntertainmentFragmentDirections
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class NewsAdapter :
    ListAdapter<com.ibsu.ibsu.domain.model.NewsItem, NewsAdapter.NewsViewHolder>(ItemDiffCallback()) {
    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var source: com.ibsu.ibsu.domain.model.NewsItem? = null
        fun bind() {
            val context = binding.root.context
            binding.apply {
                source = getItem(absoluteAdapterPosition)

                if (context.getCurrentLocale().language == georgianLocale) {
                    newsTitleTV.text = source?.headlineGe
                    dateTV.text = source?.dateGe
                } else {
                    newsTitleTV.text = source?.headlineEn
                    dateTV.text = source?.dateEn

                }

                thumbnail.loadFromUrl(source?.thumbnail)

                thumbnail.setOnClickListener {
                    navigateToDescription()
                }
                root.setOnClickListener {
                    navigateToDescription()
                }
            }
        }

        private fun navigateToDescription() {
            val action =
                EntertainmentFragmentDirections.actionEntertainmentFragmentToNewsDescriptionFragment(
                    source!!
                )
            binding.root.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.NewsItem>() {
        override fun areItemsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.NewsItem,
            newItem: com.ibsu.ibsu.domain.model.NewsItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.NewsItem,
            newItem: com.ibsu.ibsu.domain.model.NewsItem,
        ): Boolean =
            oldItem == newItem

    }
}
