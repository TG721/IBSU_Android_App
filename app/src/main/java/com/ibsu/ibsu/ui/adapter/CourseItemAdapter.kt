package com.ibsu.ibsu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibsu.ibsu.R
import com.ibsu.ibsu.domain.model.CoursesItem
import com.ibsu.ibsu.databinding.CourseItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale

class CourseItemAdapter(private val context: Context) :
    ListAdapter<com.ibsu.ibsu.domain.model.CoursesItem, CourseItemAdapter.RVViewHolder>(ItemDiffCallback()) {
    inner class RVViewHolder(private val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if (source.isCore != null) {
                    if (source.isCore) isCoreTV.text = context.getString(R.string.obligatory)
                    else isCoreTV.text = context.getString(R.string.elective)
                } else isCoreTV.visibility = View.GONE


                var finalStringValue = ""
                if (context.getCurrentLocale(context).language == georgianLocale) {
                    courseName.text = source.nameGe
                    if (source.prerequisitesGe != null)
                        source.prerequisitesGe.forEach {
                            finalStringValue += "$it;\n"
                        } else {
                        prerequisites.visibility = View.GONE
                        staticItem.visibility = View.GONE
                    }
                    if (source.description_Ge != null) {
                        seeDescription.visibility = View.VISIBLE
                        seeDescription.paintFlags =
                            seeDescription.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        val dialogView = LayoutInflater.from(context)
                            .inflate(R.layout.custom_dialog_layout, null)
                        val descriptionTextView =
                            dialogView.findViewById<TextView>(R.id.descriptionTextView)
                        descriptionTextView?.text = source.description_Ge
                        seeDescription?.setOnClickListener {
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
                    if (source.prerequisites != null) {
                        source.prerequisites.forEach {
                            finalStringValue += "$it;\n"
                        }
                    } else {
                        prerequisites.visibility = View.GONE
                        staticItem.visibility = View.GONE
                    }
                    if (source.description_En != null) {
                        seeDescription.visibility = View.VISIBLE
                        seeDescription.paintFlags =
                            seeDescription.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        seeDescription?.setOnClickListener {
                            val dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.custom_dialog_layout, null)
                            val descriptionTextView =
                                dialogView.findViewById<TextView>(R.id.descriptionTextView)
                            descriptionTextView?.text = source.description_En
                            MaterialAlertDialogBuilder(context)
                                .setTitle(context.getString(R.string.description))
                                .setView(dialogView)
                                .setNeutralButton(context.getString(R.string.ok)) { dialog, which ->
                                }
                                .show()
                        }
                    } else seeDescription.visibility = View.GONE
                }
                hours.text = "${context.getString(R.string.contact_hours_per_week)} ${source.hours}"
                if (source.prerequisitesGe != null && source.prerequisites != null)
                    prerequisites.text = finalStringValue

                ETC.text = "${context.getString(R.string.etc)}${source.ETC}"
                if (source.semesterNumber != null) semester.text =
                    context.getString(R.string.semester) + source.semesterNumber.toString()
                else semester.visibility = View.GONE

//                imageBtn.setOnClickListener {
//                    val action =
//                        EntertainmentFragmentDirections.actionEntertainmentFragmentToSingleClubFragment(
//                            source
//                        )
//                    root.findNavController().navigate(action)
//                }
                if (finalStringValue == "") prerequisites.text = context.getString(R.string.none)
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

    private class ItemDiffCallback : DiffUtil.ItemCallback<com.ibsu.ibsu.domain.model.CoursesItem>() {
        override fun areItemsTheSame(oldItem: com.ibsu.ibsu.domain.model.CoursesItem, newItem: com.ibsu.ibsu.domain.model.CoursesItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: com.ibsu.ibsu.domain.model.CoursesItem, newItem: com.ibsu.ibsu.domain.model.CoursesItem): Boolean =
            oldItem == newItem


    }
}
