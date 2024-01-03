package com.ibsu.ibsu.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.domain.model.UsefulDocsItem
import com.ibsu.ibsu.databinding.UsefulDocsItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class UsefulDocsAdapter(private val context: Context) :
    ListAdapter<com.ibsu.ibsu.domain.model.UsefulDocsItem, UsefulDocsAdapter.ItemViewHolder>(ItemDiffCallback()) {

    inner class ItemViewHolder(private val binding: UsefulDocsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(position: Int) {

            val source = getItem(absoluteAdapterPosition)
            if (context.getCurrentLocale(context).language == georgianLocale)
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

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.UsefulDocsItem>() {
        override fun areItemsTheSame(oldItem: com.ibsu.ibsu.domain.model.UsefulDocsItem, newItem: com.ibsu.ibsu.domain.model.UsefulDocsItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: com.ibsu.ibsu.domain.model.UsefulDocsItem, newItem: com.ibsu.ibsu.domain.model.UsefulDocsItem): Boolean =
            oldItem == newItem

    }

}