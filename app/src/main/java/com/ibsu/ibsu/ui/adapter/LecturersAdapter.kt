package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
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
import com.ibsu.ibsu.data.remote.model.LecturersItem
import com.ibsu.ibsu.databinding.AdminStaffItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale


class LecturersAdapter(private val context: Context) :
    ListAdapter<LecturersItem, LecturersAdapter.LecturerViewHolder>(ItemDiffCallback()) {
    inner class LecturerViewHolder(private val binding: AdminStaffItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if (source.pictureURL != null) {
                    Glide.with(imageView)
                        .load(source.pictureURL)
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
                } else {
                    Glide.with(imageView)
                        .load(R.drawable.baseline_person_24)
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
                }
                adminStaffNameTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                if (context.getCurrentLocale(context).language == "ka"){
                    adminStaffNameTV.text = source.nameGe
                    //position is name but variable represents status like M.A, Doctor
                    adminStaffPositionTV.text = source.statusGe


                } else {
                    adminStaffNameTV.text = source.nameEn
                    adminStaffPositionTV.text = source.statusEn
                }
                if(source.isAcademic)
                    invitedOrAcademic.text = context.getString(R.string.academic)
                else {
                    invitedOrAcademic.text = context.getString(R.string.invited)
                }
                binding.invitedOrAcademic.visibility = View.VISIBLE
            }
        }

        fun replaceFirstCommaWithNewLine(text: String): String {
            val indexOfComma = text.indexOf(", ")
            return if (indexOfComma != -1) {
                text.replaceFirst(", ", "\n")
            } else {
                text
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LecturerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdminStaffItemBinding.inflate(layoutInflater, parent, false)
        return LecturerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LecturerViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<LecturersItem>() {
        override fun areItemsTheSame(
            oldItem: LecturersItem,
            newItem: LecturersItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: LecturersItem,
            newItem: LecturersItem,
        ): Boolean =
            oldItem == newItem

    }
}
