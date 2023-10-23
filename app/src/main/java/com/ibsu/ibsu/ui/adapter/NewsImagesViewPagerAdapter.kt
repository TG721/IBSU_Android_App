package com.ibsu.ibsu.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.ImageViewpagerItemBinding
import com.ibsu.ibsu.extensions.loadFromUrl

class NewsImagesViewPagerAdapter(private val Images: List<String>, private val activity: Activity) :
    RecyclerView.Adapter<NewsImagesViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(val binding: ImageViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageViewpagerItemBinding.inflate(inflater, parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Images.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentImage = Images[position]
        // Use the binding instance to access views and set data
        holder.binding.imageView.loadFromUrl(currentImage)
        holder.binding.imageView.setOnTouchImageViewListener {

            activity.findViewById<ViewPager2>(R.id.viewPagerForSportNews)?.isUserInputEnabled =
                !holder.binding.imageView.isZoomed
        }

    }
}