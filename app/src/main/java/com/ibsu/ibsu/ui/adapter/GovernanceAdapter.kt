package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.GovernanceItem
import com.ibsu.ibsu.databinding.AdminStaffPortraitItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class GovernanceAdapter(private val context: Context) :
    ListAdapter<GovernanceItem, GovernanceAdapter.StaffPortraitItemHolder>(ItemDiffCallback()) {
    inner class StaffPortraitItemHolder(private val binding: AdminStaffPortraitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var alertDialog: AlertDialog? = null


        fun bind(position: Int) {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {

                Glide.with(imageView)
                    .load(source.pictureURL)
                    .transition(withCrossFade())
//                    .apply(RequestOptions.placeholderOf(R.drawable.music_club))  // Optional: Set a placeholder image
                    .into(imageView)
                val dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_dialog_layout, null)
                val descriptionTextView =
                    dialogView.findViewById<TextView>(R.id.descriptionTextView)
                if (context.getCurrentLocale(context).language == georgianLocale) {
                    adminStaffNameTV.text = source.NameGe
                    adminStaffPositionTV.text = source.governingPositionGe
                    moreInfoTV.text = source.academicPositionGe
                    if(source.descriptionGe!==null){
                        info.visibility = View.VISIBLE
                        info.setOnClickListener {
                            val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null)

                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            alertDialog =    MaterialAlertDialogBuilder(context)
                                .setTitle(context.getString(R.string.description))
                                .setView(dialogView)
                                .setNeutralButton(context.getString(R.string.ok)) { dialog, which ->
                                }
                                .show()
                            descriptionTextView.text = source.descriptionGe
                        }
                    } else info.visibility = View.GONE
                } else {
                    moreInfoTV.text = source.academicPositionEn
                    if(source.descriptionEn!==null){
                        info.visibility = View.VISIBLE
                        info.setOnClickListener {
                            val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null)

                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            alertDialog?.dismiss()
                            alertDialog =   MaterialAlertDialogBuilder(context)
                                .setTitle(context.getString(R.string.description))
                                .setView(dialogView)
                                .setNeutralButton(context.getString(R.string.ok)) { dialog, which ->
                                }
                                .show()
                            descriptionTextView.text = source.descriptionEn
                        }
                    } else info.visibility = View.GONE
                    adminStaffNameTV.text = source.NameEn
                    adminStaffPositionTV.text = source.governingPositionEn
                    moreInfoTV.text = source.academicPositionEn

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
        holder.bind(position)
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<GovernanceItem>() {
        override fun areItemsTheSame(
            oldItem: GovernanceItem,
            newItem: GovernanceItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: GovernanceItem,
            newItem: GovernanceItem,
        ): Boolean =
            oldItem == newItem

    }
}
