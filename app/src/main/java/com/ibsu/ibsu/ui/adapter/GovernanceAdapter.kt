package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.AdminStaffPortraitItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class GovernanceAdapter :
    ListAdapter<com.ibsu.ibsu.domain.model.GovernanceItem, GovernanceAdapter.StaffPortraitItemHolder>(
        ItemDiffCallback()
    ) {
    inner class StaffPortraitItemHolder(private val binding: AdminStaffPortraitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var alertDialog: AlertDialog? = null


        fun bind() {
            val context = binding.root.context
            val source = getItem(absoluteAdapterPosition)
            binding.apply {

                imageView.loadFromUrl(source.pictureURL)

                val dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_dialog_layout, null)
                val descriptionTextView =
                    dialogView.findViewById<TextView>(R.id.descriptionTextView)
                if (context.getCurrentLocale().language == georgianLocale) {
                    adminStaffNameTV.text = source.NameGe
                    adminStaffPositionTV.text = source.governingPositionGe
                    moreInfoTV.text = source.academicPositionGe
                    if (source.descriptionGe !== null) {
                        info.visibility = View.VISIBLE
                        info.setOnClickListener {
                            val dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.custom_dialog_layout, null)

                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            alertDialog = MaterialAlertDialogBuilder(context)
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
                    if (source.descriptionEn !== null) {
                        info.visibility = View.VISIBLE
                        info.setOnClickListener {
                            val dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.custom_dialog_layout, null)

                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            alertDialog?.dismiss()
                            alertDialog = MaterialAlertDialogBuilder(context)
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
        holder.bind()
    }

    private class ItemDiffCallback :
        DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.GovernanceItem>() {
        override fun areItemsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.GovernanceItem,
            newItem: com.ibsu.ibsu.domain.model.GovernanceItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: com.ibsu.ibsu.domain.model.GovernanceItem,
            newItem: com.ibsu.ibsu.domain.model.GovernanceItem,
        ): Boolean =
            oldItem == newItem

    }
}
