package com.ibsu.ibsu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibsu.ibsu.databinding.SliderItemBinding

class SliderViewPagerAdapter(private val Images: List<String>) :
    RecyclerView.Adapter<SliderViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(val binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SliderItemBinding.inflate(inflater, parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Images.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentImage = Images[position]
        // Use the binding instance to access views and set data
        Glide.with(holder.binding.imageView)
            .load(currentImage)
            .into(holder.binding.imageView)
    }
}