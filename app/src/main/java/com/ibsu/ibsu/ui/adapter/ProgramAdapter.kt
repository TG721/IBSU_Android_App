package com.ibsu.ibsu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibsu.ibsu.data.remote.model.ProgramItem
import androidx.recyclerview.widget.ListAdapter
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.ProgramItemBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.element.ProgramsFragmentDirections
import com.ibsu.ibsu.utils.PdfDownloader
import com.ibsu.ibsu.utils.Schools


class ProgramAdapter(private val context: Context, private val type: String) :
    ListAdapter<ProgramItem, ProgramAdapter.ProgramViewHolder>(ItemDiffCallback()) {
    inner class ProgramViewHolder(private val binding: ProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                if(context.getCurrentLocale(context).language=="ka")
                programBtn.text = source.programNameGe
                else programBtn.text = source.programNameEn
                when(source.School) {
                    Schools.business -> {
                        val colorResId = R.color.business
                        val color = ContextCompat.getColor(context, colorResId)
                        val colorStateList = ColorStateList.valueOf(color)
                        programBtn.backgroundTintList = colorStateList
                    }
                    Schools.computerAndArchitecture -> {
                        val colorResId = R.color.computer_and_architecture
                        val color = ContextCompat.getColor(context, colorResId)
                        val colorStateList = ColorStateList.valueOf(color)
                        programBtn.backgroundTintList = colorStateList
                    }
                    Schools.educationHumanitiesAndSocialSciences -> {
                        val colorResId = R.color.humanities_and_social
                        val color = ContextCompat.getColor(context, colorResId)
                        val colorStateList = ColorStateList.valueOf(color)
                        programBtn.backgroundTintList = colorStateList
                    }
                    Schools.lawAndStateGovernance -> {
                        val colorResId = R.color.law_and_state
                        val color = ContextCompat.getColor(context, colorResId)
                        val colorStateList = ColorStateList.valueOf(color)
                        programBtn.backgroundTintList = colorStateList
                    }
                    else -> {programBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.ibsu))}
                }
                    programBtn.setOnClickListener {
                        if(source.isReady && !source.shouldDownloadFile){
                        val action =
                            ProgramsFragmentDirections.actionProgramsFragmentToProgramDetailsFragment(
                                source.programNameEn,
                                type
                            )
                        root.findNavController().navigate(action)
                    } else if (source.isReady && source.shouldDownloadFile){
                            PdfDownloader.setFileName(source.programNameEn)
                            PdfDownloader.downloadPdfWithReceiver(context, source.fileLink!!, binding.root)
                        }
                        else {
                            Toast.makeText(context, context.getString(R.string.page_is_being_processed), Toast.LENGTH_SHORT).show()
                        }
                }

            }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProgramItemBinding.inflate(layoutInflater, parent, false)
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<ProgramItem>() {
        override fun areItemsTheSame(oldItem: ProgramItem, newItem: ProgramItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ProgramItem, newItem: ProgramItem): Boolean =
            oldItem == newItem

    }
}
