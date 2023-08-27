package com.ibsu.ibsu.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.ImageViewpagerItemBinding
import com.ortiz.touchview.TouchImageView

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
        Glide.with(holder.binding.imageView)
            .load(currentImage)
            .into(holder.binding.imageView)
        holder.binding.imageView.setOnTouchImageViewListener {
//            d("ddddddd",holder.binding.imageView.isZoomed.toString())
            if(holder.binding.imageView.isZoomed){
                activity.findViewById<ViewPager2>(R.id.viewPagerForSportNews)?.isUserInputEnabled = false
            } else activity.findViewById<ViewPager2>(R.id.viewPagerForSportNews)?.isUserInputEnabled = true
            false
        }

    }
}