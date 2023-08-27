package com.ibsu.ibsu.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val dateEn: String,
    val dateGe: String,
    val descriptionEn: String,
    val descriptionGe: String,
    val headlineEn: String,
    val headlineGe: String,
    val id: Int,
    val pictureLinksList: List<String>?,
    val thumbnail: String
) : Parcelable