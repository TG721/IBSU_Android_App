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
import com.ibsu.ibsu.data.remote.model.NewsItem
import com.ibsu.ibsu.databinding.NewsItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale

class NewsAdapter(private val context: Context) :
    ListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(ItemDiffCallback()) {
    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if (context.getCurrentLocale(context).language == "ka") {
                    newsTitleTV.text = source.headlineGe
                    dateTV.text = source.dateGe
                } else {
                    newsTitleTV.text = source.headlineEn
                    dateTV.text = source.dateEn

                }
                Glide.with(thumbnail)
                    .load(source.thumbnail)
//                    .apply(RequestOptions.placeholderOf(R.drawable.music_club))  // Optional: Set a placeholder image
                    .transition(DrawableTransitionOptions.withCrossFade())  // Optional: Add a fade-in animation
                    .into(thumbnail)
                thumbnail.setOnClickListener {
//                    val action =
//                        EntertainmentFragmentDirections.actionEntertainmentFragmentToSingleClubFragment(
//                            source
//                        )
//                    root.findNavController().navigate(action)
                }
            }
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

    private class ItemDiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
            oldItem == newItem

    }
}
