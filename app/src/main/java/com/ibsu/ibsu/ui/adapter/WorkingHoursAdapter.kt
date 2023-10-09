package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.data.remote.model.WorkingHoursItem
import com.ibsu.ibsu.databinding.WorkingHoursItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class WorkingHoursAdapter(private val context: Context) :
    ListAdapter<WorkingHoursItem, WorkingHoursAdapter.ItemViewHolder>(ItemDiffCallback()) {
    inner class ItemViewHolder(private val binding: WorkingHoursItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if (context.getCurrentLocale(context).language == georgianLocale) {
                    statement.text = source.statementGe
                    workingHours.text = source.hoursGe
                } else {
                    statement.text = source.statementEn
                    workingHours.text = source.hoursEn
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WorkingHoursItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<WorkingHoursItem>() {
        override fun areItemsTheSame(
            oldItem: WorkingHoursItem,
            newItem: WorkingHoursItem,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: WorkingHoursItem,
            newItem: WorkingHoursItem,
        ): Boolean =
            oldItem == newItem

    }
}
