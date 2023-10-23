package com.ibsu.ibsu.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

fun ImageView.loadFromUrl(url: String?, progressBar: ProgressBar? = null, size: Int? = null) {
    val glideRequest = Glide.with(this).load(url)

    // Apply override only if size is specified
    size?.let {
        glideRequest.override(size)
    }

    glideRequest.transition(DrawableTransitionOptions.withCrossFade())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                // Hide the progress bar if image loading fails
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }
        })
        .into(this)
}

fun ImageView.loadFromResource(@RawRes @DrawableRes resourceId: Int?) {
    Glide.with(this)
        .load(resourceId)
        .into(this)
}
