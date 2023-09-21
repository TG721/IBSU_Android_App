package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.ClubsItem
import com.ibsu.ibsu.data.remote.model.CoursesItem
import com.ibsu.ibsu.databinding.CourseItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.element.EntertainmentFragmentDirections

class CourseItemAdapter (private val context: Context) :
    ListAdapter<CoursesItem, CourseItemAdapter.RVViewHolder>(ItemDiffCallback()) {
    inner class RVViewHolder(private val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if(source.isCore) isCoreTV.text = context.getString(R.string.obligatory)
                else isCoreTV.text = context.getString(R.string.elective)

                var finalStringValue = ""
                if(context.getCurrentLocale(context).language=="ka") {
                    courseName.text = source.nameGe
                    source.prerequisitesGe.forEach {
                        finalStringValue += "$it;\n"
                    }
                    if(source.description_Ge!=null) {
                        seeDescription.visibility = View.VISIBLE
                        seeDescription.paintFlags =  seeDescription.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null)
                        val descriptionTextView = dialogView.findViewById<TextView>(R.id.descriptionTextView)
                        descriptionTextView.text = source.description_Ge
                        seeDescription.setOnClickListener {
                            MaterialAlertDialogBuilder(context)
                                .setTitle(context.getString(R.string.description))
                                .setView(dialogView)
                                .setNeutralButton(context.getString(R.string.ok)) { dialog, which ->
                                }
                                .show()
                        }
                    } else seeDescription.visibility = View.GONE

                } else {
                    courseName.text = source.nameEn
                    source.prerequisites.forEach {
                        finalStringValue +=  "$it;\n"
                    }
                    if(source.description_En!=null) {
                        seeDescription.visibility = View.VISIBLE
                        seeDescription.paintFlags =  seeDescription.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        seeDescription.setOnClickListener {
                            val dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.custom_dialog_layout, null)
                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            descriptionTextView.text = source.description_En
                            MaterialAlertDialogBuilder(context)
                                .setTitle(context.getString(R.string.description))
                                .setView(dialogView)
                                .setNeutralButton(context.getString(R.string.ok)) { dialog, which ->
                                }
                                .show()
                        }
                    } else seeDescription.visibility = View.GONE
                }
                prerequisites.text = finalStringValue
                ETC.text = "${context.getString(R.string.etc)}${source.ETC}"
                if(semester!=null) semester.text = context.getString(R.string.semester) + source.semesterNumber

//                imageBtn.setOnClickListener {
//                    val action =
//                        EntertainmentFragmentDirections.actionEntertainmentFragmentToSingleClubFragment(
//                            source
//                        )
//                    root.findNavController().navigate(action)
//                }
                if(finalStringValue=="") prerequisites.text = context.getString(R.string.none)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CourseItemBinding.inflate(layoutInflater, parent, false)
        return RVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<CoursesItem>() {
        override fun areItemsTheSame(oldItem: CoursesItem, newItem: CoursesItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CoursesItem, newItem: CoursesItem): Boolean =
            oldItem == newItem


    }
}
