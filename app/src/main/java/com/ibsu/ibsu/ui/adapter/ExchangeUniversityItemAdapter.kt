package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.ExchangeUniversityItem
import com.ibsu.ibsu.databinding.ExchangeUniversityItemBinding

class ExchangeUniversityItemAdapter(private val context: Context) :
    ListAdapter<ExchangeUniversityItem, ExchangeUniversityItemAdapter.ViewHolder>(ItemDiffCallback()) {
    inner class ViewHolder(private val binding: ExchangeUniversityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                universityNameTV.text = source.name
                deadline.text = "Deadline" +" " + source.deadline
                country.text = "Country" +" " + source.county
                moreDetails.text = source.Details
                if (source.image != null) {
                    Glide.with(imageView)
                        .load(source.image)
                        .override(800)
//                    .apply(RequestOptions.placeholderOf(R.drawable.music_club))  // Optional: Set a placeholder image
                        .transition(DrawableTransitionOptions.withCrossFade())  // Optional: Add a fade-in animation
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // Hide the progress bar if image loading fails
                                progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        .into(imageView)
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

        private class ItemDiffCallback : DiffUtil.ItemCallback<ExchangeUniversityItem>() {
            override fun areItemsTheSame(
                oldItem: ExchangeUniversityItem,
                newItem: ExchangeUniversityItem,
            ): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ExchangeUniversityItem,
                newItem: ExchangeUniversityItem,
            ): Boolean =
                oldItem == newItem

        }
    }
