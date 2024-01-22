package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.domain.model.ContactInfoItem
import com.ibsu.ibsu.databinding.ContactInfoItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class ContactInfoItemAdapter :
    ListAdapter<ContactInfoItem, ContactInfoItemAdapter.ItemViewHolder>(ItemDiffCallback()) {
    inner class ItemViewHolder(private val binding: ContactInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {




        fun bind() {
            val context = binding.root.context
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                email.paintFlags = binding.email.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                number.paintFlags = binding.number.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                if(source.nameEn!=null) {
                    statement.visibility = View.VISIBLE
                    if (context.getCurrentLocale().language == georgianLocale)
                        statement.text = source.nameGe
                    else statement.text = source.nameEn
                } else { statement.visibility = View.GONE }
                email.text = source.Email
                number.text = source.Phone
                extra.text = source.extra
                email.setOnClickListener {
                    openEmail(source.Email, context)
                }
                number.setOnClickListener {
                    openNumber(source.Phone, context)
                }
            }

        }


        private fun openNumber(phoneNumber: String, context: Context) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            val packageManager = context.packageManager
            if (intent.resolveActivity(packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No phone app found.", Toast.LENGTH_SHORT).show()
            }

        }

        private fun openEmail(email: String, context: Context) {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactInfoItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<ContactInfoItem>() {
        override fun areItemsTheSame(oldItem: ContactInfoItem, newItem: ContactInfoItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ContactInfoItem,
            newItem: ContactInfoItem,
        ): Boolean =
            oldItem == newItem

    }
}
