package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
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
import com.ibsu.ibsu.data.remote.model.AdministrationItem
import com.ibsu.ibsu.databinding.AdminStaffItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale

class AdminStaffItemAdapter(private val context: Context, private val emailVisibility: Boolean = false) :
    ListAdapter<AdministrationItem, AdminStaffItemAdapter.StaffViewHolder>(ItemDiffCallback()) {
    inner class StaffViewHolder(private val binding: AdminStaffItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if(emailVisibility) {
                    imageViewMail.visibility = View.VISIBLE
                }

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
                if (context.getCurrentLocale(context).language == "ka")
                    adminStaffNameTV.text = source.nameGe
                else adminStaffNameTV.text = source.nameEn
                var positionValue: String
                if (context.getCurrentLocale(context).language == "ka")
                    positionValue = source.positionGe
                else positionValue = source.positionEn
                if (positionValue.length > 20) {
                    var text = replaceFirstCommaWithNewLine(positionValue)
                    if(!text.contains('\n')) {
                          text =  text.plus("\n")}
                    adminStaffPositionTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                    val spannableString = SpannableString(text)
                    val startIndex = 0 // Index where the desired portion of the text starts
                    val endIndex =
                        text.indexOf("\n") // Index where the desired portion of the text ends

                    val relativeSizeSpan =
                        RelativeSizeSpan(1.5f) // Text size multiplier (1.5 times the original size)

                    spannableString.setSpan(
                        relativeSizeSpan,
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    adminStaffPositionTV.text = spannableString
                } else {
                    adminStaffPositionTV.text = positionValue
                    adminStaffPositionTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                }
            }
        }

        fun replaceFirstCommaWithNewLine(text: String): String {
            val indexOfComma = text.indexOf(", ")
             if (indexOfComma != -1) {
                return text.replaceFirst(", ", "\n")
            } else {
                val indexOfSemiColon = text.indexOf("; ")
                if (indexOfSemiColon != -1) return text.replaceFirst("; ", "\n")
                 else return text
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdminStaffItemBinding.inflate(layoutInflater, parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<AdministrationItem>() {
        override fun areItemsTheSame(
            oldItem: AdministrationItem,
            newItem: AdministrationItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: AdministrationItem,
            newItem: AdministrationItem,
        ): Boolean =
            oldItem == newItem

    }
}
