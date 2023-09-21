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
import com.ibsu.ibsu.data.remote.model.ProgramAdminItem
import com.ibsu.ibsu.databinding.ProgramAdminItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale

class ProgramAdminAdapter(private val context: Context) :
    ListAdapter<ProgramAdminItem, ProgramAdminAdapter.ItemViewHolder>(ItemDiffCallback()) {
    inner class ItemViewHolder(private val binding: ProgramAdminItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                email.paintFlags = binding.email.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                number.paintFlags = binding.number.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                if (context.getCurrentLocale(context).language == "ka") {
                    name.text = source.nameGe
                    position.text = source.positionGe
                } else {
                    name.text = source.nameEn
                    position.text = source.positionEn
                }
                email.text = source.email
                if(source.room!=null) room.text = source.room
                else  room.visibility = View.GONE
                email.setOnClickListener {
                    openEmail(source.email)
                }
                if (source.phone != null) {
                    number.setOnClickListener {
                        openNumber(source.phone)
                    }
                    number.text = source.phone
                    number.visibility = View.VISIBLE
                } else {
                    number.visibility = View.GONE
                }
            }

        }
    }

    private fun openNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProgramAdminItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<ProgramAdminItem>() {
        override fun areItemsTheSame(
            oldItem: ProgramAdminItem,
            newItem: ProgramAdminItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ProgramAdminItem,
            newItem: ProgramAdminItem,
        ): Boolean =
            oldItem == newItem

    }
}
