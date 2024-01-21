package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.databinding.FacebookFanPageItemBinding
import com.ibsu.ibsu.extensions.loadFromUrl

class FanPagesAdapter :
    ListAdapter<com.ibsu.ibsu.domain.model.FBFanPagesItem, FanPagesAdapter.FBFanPageViewHolder>(
        ItemDiffCallback()
    ) {
    inner class FBFanPageViewHolder(private val binding: FacebookFanPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val context = binding.root.context
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                imageBtn.loadFromUrl(source.pictureURL)

                binding.titleTV.text = source.name
                imageBtn.setOnClickListener {
                    openFacebookPage(source.link, context)
                }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FBFanPageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FacebookFanPageItemBinding.inflate(layoutInflater, parent, false)
        return FBFanPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FBFanPageViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback :
        DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.FBFanPagesItem>() {
        override fun areItemsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.FBFanPagesItem,
            newItem: com.ibsu.ibsu.domain.model.FBFanPagesItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.FBFanPagesItem,
            newItem: com.ibsu.ibsu.domain.model.FBFanPagesItem,
        ): Boolean =
            oldItem == newItem

    }

    private fun openFacebookPage(facebookPageUrl: String, context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl)))
    }
}
