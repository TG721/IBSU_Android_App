package com.ibsu.ibsu.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.ibsu.ibsu.NewsDescriptionFragmentDirections
import com.ibsu.ibsu.data.remote.model.UsefulDocsItem
import com.ibsu.ibsu.databinding.UsefulDocsItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale

class UsefulDocsAdapter(private val context: Context) : ListAdapter<UsefulDocsItem, UsefulDocsAdapter.ItemViewHolder>(ItemDiffCallback()) {

    inner class ItemViewHolder(private val binding: UsefulDocsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(position: Int) {

            val source = getItem(absoluteAdapterPosition)
            if (context.getCurrentLocale(context).language == "ka")
                binding.textView.text = source.nameGe
            else binding.textView.text = source.nameEn

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source.link))

                // start activity
                context.startActivity(intent);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UsefulDocsItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<UsefulDocsItem>() {
        override fun areItemsTheSame(oldItem: UsefulDocsItem, newItem: UsefulDocsItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UsefulDocsItem, newItem: UsefulDocsItem): Boolean =
            oldItem == newItem

    }

}