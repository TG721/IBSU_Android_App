package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.LecturersItem
import com.ibsu.ibsu.databinding.AdminStaffItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.loadFromResource
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale


class LecturersAdapter(private val context: Context, private val emailVisibility: Boolean = false) :
    ListAdapter<LecturersItem, LecturersAdapter.LecturerViewHolder>(ItemDiffCallback()) {
    inner class LecturerViewHolder(private val binding: AdminStaffItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if (emailVisibility) {
                    if (source.email != null)
                        imageViewMail.visibility = View.VISIBLE
                }

                imageViewMail.setOnClickListener {
                    openEmail(source.email!!)
                }

                if (source.pictureURL != null) {
                    imageView.loadFromUrl(source.pictureURL, progressBar)
                } else {
                    imageView.loadFromResource(R.drawable.baseline_person_24)
                    binding.progressBar.visibility = View.GONE
                }
                adminStaffNameTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                if (context.getCurrentLocale(context).language == georgianLocale) {
                    adminStaffNameTV.text = source.nameGe
                    //position is name but variable represents status like M.A, Doctor
                    adminStaffPositionTV.text = source.statusGe


                } else {
                    adminStaffNameTV.text = source.nameEn
                    adminStaffPositionTV.text = source.statusEn
                }
                if (source.isAcademic)
                    invitedOrAcademic.text = context.getString(R.string.academic)
                else {
                    invitedOrAcademic.text = context.getString(R.string.invited)
                }
                binding.invitedOrAcademic.visibility = View.VISIBLE
            }
        }

        private fun openEmail(email: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            }
            val packageManager = context.packageManager
            if (intent.resolveActivity(packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No email app found.", Toast.LENGTH_SHORT).show()
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
