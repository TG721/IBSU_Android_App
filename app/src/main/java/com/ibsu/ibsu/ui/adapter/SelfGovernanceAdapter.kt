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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.ibsu.ibsu.data.remote.model.SelfGovernanceItem
import com.ibsu.ibsu.databinding.AdminStaffPortraitItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale

class SelfGovernanceAdapter(private val context: Context) :
    ListAdapter<SelfGovernanceItem, SelfGovernanceAdapter.StaffPortraitItemHolder>(ItemDiffCallback()) {
    inner class StaffPortraitItemHolder(private val binding: AdminStaffPortraitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                Glide.with(imageView)
                    .load(source.pictureURL)
                    .transition(withCrossFade())
//                    .apply(RequestOptions.placeholderOf(R.drawable.music_club))  // Optional: Set a placeholder image
                    .into(imageView)

                if (context.getCurrentLocale(context).language == "ka") {
                    adminStaffNameTV.text = source.NameGe
                    adminStaffPositionTV.text = source.governingPositionGe
                    moreInfoTV.text = source.studyingPositionGe
                } else {
                    adminStaffNameTV.text = source.NameEn
                    adminStaffPositionTV.text = source.governingPositionEn
                    moreInfoTV.text = source.studyingPositionEn

                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffPortraitItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdminStaffPortraitItemBinding.inflate(layoutInflater, parent, false)
        return StaffPortraitItemHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffPortraitItemHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<SelfGovernanceItem>() {
        override fun areItemsTheSame(
            oldItem: SelfGovernanceItem,
            newItem: SelfGovernanceItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: SelfGovernanceItem,
            newItem: SelfGovernanceItem,
        ): Boolean =
            oldItem == newItem

    }
}
